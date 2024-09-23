package kz.oj.tinkoffhw5.service;

import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationUpdateRequest;

import java.util.List;

public interface LocationService {

    List<Location> findAll();

    Location findById(String id);

    void create(LocationCreateRequest request);

    void update(String id, LocationUpdateRequest request);

    void delete(String id);
}
