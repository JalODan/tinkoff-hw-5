package kz.oj.tinkoffhw5.service;

import kz.oj.tinkoffhw5.web.rest.v1.dto.PlaceDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.PlaceCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.PlaceUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface PlaceService {

    List<PlaceDto> findAll();

    PlaceDto findById(UUID id);

    PlaceDto create(PlaceCreateRequest request);

    PlaceDto update(UUID id, PlaceUpdateRequest request);

    void delete(UUID id);
}
