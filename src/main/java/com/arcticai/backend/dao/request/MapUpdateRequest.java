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
public class MapUpdateRequest {
    private String name;
    private String description;
    private Integer zoom;
    private Double latitude;
    private Double longitude;
    private List<LayerRequest> layers;
    private List<MarkerRequest> markers;
    private List<RouteRequest> routes;
}
