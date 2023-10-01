package com.arcticai.backend.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
