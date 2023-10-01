package com.arcticai.backend.service.impl;

import com.arcticai.backend.dao.request.MapUpdateRequest;
import com.arcticai.backend.entities.Map;
import com.arcticai.backend.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.arcticai.backend.service.MapService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final MapRepository mapRepository;

    public Map saveMap(Map map) {
        return mapRepository.save(map);
    }

    public List<Map> getMapsByUserId(Integer userId) {
        return mapRepository.findAllByUserId(userId);
    }

    public void deleteMapById(Integer mapId) {
        mapRepository.deleteById(mapId);
    }

    public boolean doesMapBelongToUser(Integer mapId, Integer userId) {
        return mapRepository.findById(mapId)
                .map(map -> map.getUser().getId().equals(userId))
                .orElse(false);
    }

    public Map updateMap(Integer mapId, MapUpdateRequest request) {
        return mapRepository.findById(mapId).map(map -> {
            if (request.getName() != null) map.setName(request.getName());
            if (request.getDescription() != null) map.setDescription(request.getDescription());
            if (request.getZoom() != null) map.setZoom(request.getZoom());
            if (request.getLatitude() != null) map.setLatitude(request.getLatitude());
            if (request.getLongitude() != null) map.setLongitude(request.getLongitude());

            return mapRepository.save(map); // Save and return the updated map
        }).orElseThrow(() -> new RuntimeException("Map not found"));
    }

}
