package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.mapper.LocationMapper;
import kz.oj.tinkoffhw5.repository.LocationRepository;
import kz.oj.tinkoffhw5.service.LocationService;
import kz.oj.tinkoffhw5.web.rest.v1.dto.LocationDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    @Override
    public List<LocationDto> findAll() {

        return locationRepository.findAll().stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDto findById(UUID id) {

        return locationRepository.findById(id)
                .map(locationMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Локация не найдена по id=" + id));
    }

    @Override
    public LocationDto create(LocationCreateRequest request) {

        Location location = Location.builder()
                .id(UUID.randomUUID())
                .slug(request.getSlug())
                .name(request.getName())
                .build();

        return locationMapper.toDto(locationRepository.save(location));
    }

    @Override
    public LocationDto update(UUID id, LocationUpdateRequest request) {

        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Локация не найдена по id=" + id));

        if (request.getSlug() != null) {
            location.setSlug(request.getSlug());
        }

        if (request.getName() != null) {
            location.setName(request.getName());
        }

        return locationMapper.toDto(locationRepository.save(location));
    }

    @Override
    public void delete(UUID id) {

        locationRepository.deleteById(id);
    }
}
