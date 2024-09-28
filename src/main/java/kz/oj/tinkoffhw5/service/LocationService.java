package kz.oj.tinkoffhw5.service;

import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface LocationService {

    List<Location> findAll();

    Location findById(UUID id);

    Location create(LocationCreateRequest request);

    Location update(UUID id, LocationUpdateRequest request);

    void delete(UUID id);
}
