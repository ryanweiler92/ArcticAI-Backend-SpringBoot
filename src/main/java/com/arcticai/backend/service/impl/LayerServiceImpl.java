package com.arcticai.backend.service.impl;

import com.arcticai.backend.dao.request.MapUpdateRequest;
import com.arcticai.backend.entities.Layer;
import com.arcticai.backend.repository.LayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.arcticai.backend.service.LayerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LayerServiceImpl {
    private final LayerRepository layerRepository;

    public Layer saveLayer(Layer layer) { return layerRepository.save(layer); }

    public List<Layer> getLayersByMapId(Integer mapId) {
        return layerRepository.findAllByMapId(mapId);
    }

    public void deleteLayerById(Integer layerId) { layerRepository.deleteById(layerId); }
}
