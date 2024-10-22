package kz.oj.tinkoffhw5.web.rest.v1.controller;

import kz.oj.tinkoffhw5.aop.Timed;
import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.service.LocationService;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@Slf4j
@Timed
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> findAll() {

        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody LocationCreateRequest request) {

        return ResponseEntity.ok(locationService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@PathVariable UUID id, @RequestBody LocationUpdateRequest request) {

        return ResponseEntity.ok(locationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        locationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
