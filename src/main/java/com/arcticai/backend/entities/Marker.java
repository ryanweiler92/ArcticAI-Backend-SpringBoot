package com.arcticai.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "marker")
public class Marker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "map_id") // The column in this table that is a foreign key to map.
    private Map map;

    @Column
    private String clientId;

    @Column(length = 255, nullable = false)
    private String location;

    @Column
    private Double latitude;

    @Column
    private Double longitude;
}
