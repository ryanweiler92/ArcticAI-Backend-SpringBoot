package com.arcticai.backend.controller;

import com.arcticai.backend.dao.request.LayerRequest;
import com.arcticai.backend.dao.request.MapRequest;
import com.arcticai.backend.dao.request.MapUpdateRequest;
import com.arcticai.backend.dao.request.MarkerRequest;
import com.arcticai.backend.dao.request.RouteRequest;
import com.arcticai.backend.entities.*;

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

        // Convert layers from the incoming map request to Layer entities and set them to the map
        List<Layer> layers = map.getLayers().stream().map(layerRequest -> {
            Layer layer = new Layer();
            layer.setTitle(layerRequest.getTitle());
            layer.setOrder(layerRequest.getOrder());
            layer.setVisibility(layerRequest.getVisibility());
            layer.setMap(map); // Set the map to the layer
            return layer;
        }).collect(Collectors.toList());

        map.setLayers(layers); // Set the list of layers to the map
        map.setUser(user);

        List<Marker> markers = map.getMarkers().stream().map(markerRequest -> {
            Marker marker = new Marker();
            marker.setClientId(markerRequest.getClientId());
            marker.setLocation(markerRequest.getLocation());
            marker.setLatitude(markerRequest.getLatitude());
            marker.setLongitude(markerRequest.getLongitude());
            marker.setMap(map);  // Set the map to the marker
            return marker;
        }).collect(Collectors.toList());

        map.setMarkers(markers); // Set the list of markers to the map

        List<Route> routes = map.getRoutes().stream().map(routeRequest -> {
            Route route = new Route();
            route.setId(routeRequest.getId());
            route.setStartCoords(routeRequest.getStartCoords());
            route.setEndCoords(routeRequest.getEndCoords());
            route.setMarkersIds(routeRequest.getMarkersIds());
            route.setRouteName(routeRequest.getRouteName());
            route.setColor(routeRequest.getColor());
            route.setMap(map);  // Set the map to the route
            return route;
        }).collect(Collectors.toList());

        map.setRoutes(routes); // Set the list of routes to the map

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
            Map existingMap = mapService.getMapById(mapId); // Fetch the existing map

            // Update the Map properties
            existingMap.setName(request.getName());
            existingMap.setDescription(request.getDescription());
//            existingMap.setSaveSlot(request.getSaveSlot());
            existingMap.setZoom(request.getZoom());
            existingMap.setLatitude(request.getLatitude());
            existingMap.setLongitude(request.getLongitude());

            // Convert markers from the request to Marker entities
            List<Marker> newMarkers = request.getMarkers().stream().map(markerRequest -> {
                Marker marker = new Marker();
                marker.setClientId(markerRequest.getClientId());
                marker.setLocation(markerRequest.getLocation());
                marker.setLatitude(markerRequest.getLatitude());
                marker.setLongitude(markerRequest.getLongitude());
                marker.setMap(existingMap);  // Set the existing map to the marker
                return marker;
            }).collect(Collectors.toList());

            existingMap.getMarkers().clear();  // Clear existing markers. Orphaned markers will be deleted.
            existingMap.getMarkers().addAll(newMarkers); // Add the new markers

            // Convert routes from the request to Route entities
            List<Route> newRoutes = request.getRoutes().stream().map(routeRequest -> {
                Route route = new Route();
                route.setId(routeRequest.getId());
                route.setStartCoords(routeRequest.getStartCoords());
                route.setEndCoords(routeRequest.getEndCoords());
                route.setMarkersIds(routeRequest.getMarkersIds());
                route.setRouteName(routeRequest.getRouteName());
                route.setColor(routeRequest.getColor());
                route.setMap(existingMap);  // Set the existing map to the route
                return route;
            }).collect(Collectors.toList());

            existingMap.getRoutes().clear();  // Clear existing routes. Orphaned routes will be deleted.
            existingMap.getRoutes().addAll(newRoutes); // Add the new routes

            Map updatedMap = mapService.saveMap(existingMap);  // Save the updated map with the layers and markers

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

        // Convert layers from the map entity to LayerRequest DTOs
        List<LayerRequest> layers = map.getLayers().stream().map(layer -> {
            LayerRequest layerDto = new LayerRequest();
            layerDto.setId(layer.getId());
            layerDto.setTitle(layer.getTitle());
            layerDto.setOrder(layer.getOrder());
            layerDto.setVisibility(layer.getVisibility());
            return layerDto;
        }).collect(Collectors.toList());

        dto.setLayers(layers); // Set the layers to the map DTO

        // Convert markers from the map entity to MarkerRequest DTOs
        List<MarkerRequest> markerDTOs = map.getMarkers().stream().map(marker -> {
            MarkerRequest markerDto = new MarkerRequest();
            markerDto.setId(marker.getId());
            markerDto.setClientId(marker.getClientId());
            markerDto.setLocation(marker.getLocation());
            markerDto.setLatitude(marker.getLatitude());
            markerDto.setLongitude(marker.getLongitude());
            return markerDto;
        }).collect(Collectors.toList());

        dto.setMarkers(markerDTOs); // Set the markers to the map DTO

        List<RouteRequest> routeDTOs = map.getRoutes().stream().map(route -> {
            RouteRequest routeDto = new RouteRequest();
            routeDto.setId(route.getId());
            routeDto.setStartCoords(route.getStartCoords());
            routeDto.setEndCoords(route.getEndCoords());
            routeDto.setMarkersIds(route.getMarkersIds());
            routeDto.setRouteName(route.getRouteName());
            routeDto.setColor(route.getColor());
            return routeDto;
        }).collect(Collectors.toList());

        dto.setRoutes(routeDTOs);

        return dto;
    }

}

