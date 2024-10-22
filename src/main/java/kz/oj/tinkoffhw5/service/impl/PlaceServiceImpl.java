package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.entity.Place;
import kz.oj.tinkoffhw5.mapper.PlaceMapper;
import kz.oj.tinkoffhw5.repository.PlaceRepository;
import kz.oj.tinkoffhw5.service.PlaceService;
import kz.oj.tinkoffhw5.web.rest.v1.dto.PlaceDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.PlaceCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.PlaceUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceServiceImpl implements PlaceService {

    private final PlaceMapper placeMapper;
    private final PlaceRepository placeRepository;

    @Override
    public List<PlaceDto> findAll() {

        return placeRepository.findAll().stream()
                .map(placeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlaceDto findById(UUID id) {

        return placeRepository.findById(id)
                .map(placeMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Локация не найдена по id=" + id));
    }

    @Override
    public PlaceDto create(PlaceCreateRequest request) {

        Place place = Place.builder()
                .id(UUID.randomUUID())
                .slug(request.getSlug())
                .name(request.getName())
                .build();

        return placeMapper.toDto(placeRepository.save(place));
    }

    @Override
    public PlaceDto update(UUID id, PlaceUpdateRequest request) {

        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Локация не найдена по id=" + id));

        if (request.getSlug() != null) {
            place.setSlug(request.getSlug());
        }

        if (request.getName() != null) {
            place.setName(request.getName());
        }

        return placeMapper.toDto(placeRepository.save(place));
    }

    @Override
    public void delete(UUID id) {

        placeRepository.deleteById(id);
    }
}
