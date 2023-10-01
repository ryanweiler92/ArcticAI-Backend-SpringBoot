package com.arcticai.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arcticai.backend.entities.Map;
public interface MapRepository extends JpaRepository<Map, Integer> {

    // Find all maps associated with a specific user_id
    List<Map> findAllByUserId(Integer userId);

    Map findMapById(Integer mapId);

}
