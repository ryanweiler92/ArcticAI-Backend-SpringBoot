package com.arcticai.backend.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {
    private String id;
//    private List<Double> startCoords;
//    private List<Double> endCoords;
    private List<String> markersIds;
    private Double startLat;
    private Double startLon;
    private Double endLat;
    private Double endLon;
    private String routeName;
    private String color;
}
