package com.arcticai.backend.service;

import com.arcticai.backend.dao.request.MapUpdateRequest;
import com.arcticai.backend.entities.Map;

import java.util.List;

public interface MapService {
    Map saveMap(Map map);

    List<Map> getMapsByUserId(Integer userId);

    Map getMapById(Integer mapId);

    void deleteMapById(Integer mapId);

    boolean doesMapBelongToUser(Integer mapId, Integer userId);

    Map updateMap(Integer mapId, MapUpdateRequest request);
}
