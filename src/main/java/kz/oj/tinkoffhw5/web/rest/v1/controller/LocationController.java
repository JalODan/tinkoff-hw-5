package kz.oj.tinkoffhw5.web.rest.v1.controller;

import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.service.LocationService;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> findAll() {

        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable("id") String id) {

        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody LocationCreateRequest request) {

        locationService.create(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody LocationUpdateRequest request) {

        locationService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        locationService.delete(id);
        return ResponseEntity.ok().build();
    }
}