package com.arcticai.backend.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkerRequest {
    private Integer id;
    private String clientId;
    private String location;
    private Double latitude;
    private Double longitude;
}
