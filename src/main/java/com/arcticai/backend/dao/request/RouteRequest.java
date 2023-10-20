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
    private String startMarkerId;
    private String endMarkerId;
    private Double startLat;
    private Double startLon;
    private Double endLat;
    private Double endLon;
    private String routeName;
    private String color;
}
