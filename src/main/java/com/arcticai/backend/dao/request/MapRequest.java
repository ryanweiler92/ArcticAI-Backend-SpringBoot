package com.arcticai.backend.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapRequest {
    private Integer id;
    private String name;
    private String description;
    private Integer saveSlot;
    private Integer zoom;
    private Double latitude;
    private Double longitude;
    private Date createdAt;
    private Date updatedAt;
}
