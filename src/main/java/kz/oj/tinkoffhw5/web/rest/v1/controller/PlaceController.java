package kz.oj.tinkoffhw5.web.rest.v1.controller;

import kz.oj.tinkoffhw5.aop.Timed;
import kz.oj.tinkoffhw5.service.PlaceService;
import kz.oj.tinkoffhw5.web.rest.v1.dto.PlaceDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.PlaceCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.PlaceUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@Slf4j
@Timed
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceDto>> findAll() {

        return ResponseEntity.ok(placeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> findById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(placeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlaceDto> create(@RequestBody PlaceCreateRequest request) {

        return ResponseEntity.ok(placeService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDto> update(@PathVariable UUID id, @RequestBody PlaceUpdateRequest request) {

        return ResponseEntity.ok(placeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        placeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
