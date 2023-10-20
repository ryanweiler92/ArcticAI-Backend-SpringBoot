package com.arcticai.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "route")
public class Route {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "map_id")
    private Map map;

    @Column
    private String startMarkerId;

    @Column
    private String endMarkerId;

    @Column
    private Double startLat;

    @Column
    private Double startLon;

    @Column
    private Double endLat;

    @Column
    private Double endLon;

    @Column
    private String routeName;

    @Column
    private String color;
}
