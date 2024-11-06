package org.example.locationdistanceservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.locationdistanceservice.dto.GeoLocationResponse;
import org.example.locationdistanceservice.dto.PositionRequest;
import org.example.locationdistanceservice.service.GeoLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GeoLocationController {
    private final GeoLocationService geoLocationService;

    @GetMapping()
    public ResponseEntity<GeoLocationResponse> getGeoLocation(@Valid @RequestBody PositionRequest request) {
        return ResponseEntity.ok(geoLocationService.getGeoLocation(request));
    }
}
