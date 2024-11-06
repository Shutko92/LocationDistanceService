package org.example.locationdistanceservice.mapper;

import org.example.locationdistanceservice.dto.GeoLocationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeoLocationMapper {
    GeoLocationResponse toGeoLocationResponse( double[] startPos, double[] endPos, String startAddress, String endAddress, double distance);
}
