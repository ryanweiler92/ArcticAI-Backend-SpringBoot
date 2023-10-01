package com.arcticai.backend.service;

import com.arcticai.backend.entities.Layer;

import java.util.List;

public interface LayerService {

    Layer saveLayer(Layer layer);

    List<Layer> getLayersByMapId(Integer mapId);

    void deleteLayerById(Integer layerId);
}
