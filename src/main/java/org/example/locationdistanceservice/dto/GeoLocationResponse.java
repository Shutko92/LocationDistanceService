package org.example.locationdistanceservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoLocationResponse {
    private double[] startPos;
    private double[] endPos;
    private String startAddress;
    private String endAddress;
    private long distance;
}
