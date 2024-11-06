package org.example.locationdistanceservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.locationdistanceservice.dto.GeoLocationResponse;
import org.example.locationdistanceservice.dto.PositionRequest;
import org.example.locationdistanceservice.mapper.GeoLocationMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GeoLocationService {
    private final GeoLocationMapper geoLocationMapper;
    private final String YANDEX_GEOCODER_URL = "https://geocode-maps.yandex.ru/1.x/?geocode=%s&apikey=%s&format=json";
    @Value("${yandex_geocoder.api_key}")
    private String API_KEY;

    public GeoLocationResponse getGeoLocation(PositionRequest request) {
        double[] startPos = request.getStartPos();
        double[] endPos = request.getEndPos();

        String startAddress = getAddress(startPos);
        String endAddress = getAddress(endPos);
        double distance = calculateDistance(startPos[0], startPos[1], endPos[0], endPos[1]);

        return geoLocationMapper.toGeoLocationResponse(startPos, endPos, startAddress, endAddress, distance);
    }

    private String getAddress(double[] position) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(YANDEX_GEOCODER_URL, position[1] + "," + position[0], API_KEY);
            HttpGet request = new HttpGet(url);

            try (CloseableHttpResponse response = client.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);

                return rootNode.path("response").path("GeoObjectCollection")
                        .path("featureMember").get(0).path("GeoObject")
                        .path("metaDataProperty")
                        .path("GeocoderMetaData")
                        .path("text").asText();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
