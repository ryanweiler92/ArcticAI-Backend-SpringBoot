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
@Table(name = "layer")
public class Layer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "map_id") // The column in this table that is a foreign key to map.
    private Map map;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(name = "layer_order", nullable = false)
    private Integer order;

    @Column(nullable = false)
    private Boolean visibility;
}
