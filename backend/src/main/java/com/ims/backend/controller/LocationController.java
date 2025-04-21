package com.ims.backend.controller;

import com.ims.backend.dto.LocationDTO;
import com.ims.backend.entity.Location;
import com.ims.backend.mapper.LocationMapper;
import com.ims.backend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationController(LocationService locationService, LocationMapper locationMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        List<LocationDTO> locations = locationService.getAllLocations().stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.getLocationById(id);
        return location.map(loc -> ResponseEntity.ok(locationMapper.toDto(loc)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO dto) {
        Location saved = locationService.saveLocation(locationMapper.toEntity(dto));
        return ResponseEntity.ok(locationMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Long id, @RequestBody LocationDTO dto) {
        Optional<Location> existing = locationService.getLocationById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();

        Location updated = locationMapper.toEntity(dto);
        updated.setId(id);
        Location saved = locationService.saveLocation(updated);
        return ResponseEntity.ok(locationMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
