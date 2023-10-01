package com.arcticai.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arcticai.backend.entities.Layer;
public interface LayerRepository extends JpaRepository<Layer, Integer> {
    // Find all layers associated with a specific map
    List<Layer> findAllByMapId(Integer mapId);
}
