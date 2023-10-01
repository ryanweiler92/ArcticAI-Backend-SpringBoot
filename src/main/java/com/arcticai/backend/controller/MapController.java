package com.arcticai.backend.controller;

import com.arcticai.backend.dao.request.MapRequest;
import com.arcticai.backend.dao.request.MapUpdateRequest;
import com.arcticai.backend.entities.Map;

import com.arcticai.backend.entities.User;
import com.arcticai.backend.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.arcticai.backend.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/maps")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<MapRequest> createMap(@RequestBody Map map, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());

        map.setUser(user);
        Map savedMap = mapService.saveMap(map);

        return ResponseEntity.ok(convertToDTO(savedMap));
    }

    @GetMapping
    public ResponseEntity<List<MapRequest>> getMapsForAuthenticatedUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());

        List<Map> maps = mapService.getMapsByUserId(user.getId());

        List<MapRequest> responseMaps = maps.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseMaps);
    }

    @DeleteMapping("/{mapId}")
    public ResponseEntity<Void> deleteMap(@PathVariable Integer mapId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());

        // Check if map belongs to the authenticated user before deleting.
        if (mapService.doesMapBelongToUser(mapId, user.getId())) {
            mapService.deleteMapById(mapId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{mapId}")
    public ResponseEntity<MapRequest> updateMap(@PathVariable Integer mapId,
                                                @RequestBody MapUpdateRequest request,
                                                Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());

        if (mapService.doesMapBelongToUser(mapId, user.getId())) {
            Map updatedMap = mapService.updateMap(mapId, request);
            return ResponseEntity.ok(convertToDTO(updatedMap));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private MapRequest convertToDTO(Map map) {
        MapRequest dto = new MapRequest();

        dto.setId(map.getId());
        dto.setName(map.getName());
        dto.setDescription(map.getDescription());
        dto.setSaveSlot(map.getSaveSlot());
        dto.setZoom(map.getZoom());
        dto.setLatitude(map.getLatitude());
        dto.setLongitude(map.getLongitude());
        dto.setCreatedAt(map.getCreatedAt());
        dto.setUpdatedAt(map.getUpdatedAt());
        return dto;
    }

}

