package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.repository.LocationRepository;
import kz.oj.tinkoffhw5.service.LocationService;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<Location> findAll() {

        return locationRepository.findAll();
    }

    @Override
    public Location findById(String id) {

        return locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Локация не найдена по id=" + id));
    }

    @Override
    public void create(LocationCreateRequest request) {

        if (locationRepository.existsById(request.getSlug())) {
            throw new IllegalArgumentException("Дублирующееся значение");
        }

        Location location = Location.builder()
                .id(request.getSlug())
                .slug(request.getSlug())
                .name(request.getName())
                .build();

        locationRepository.save(location);
    }

    @Override
    public void update(String id, LocationUpdateRequest request) {

        Location location = findById(id);
        location.setName(request.getName());

        locationRepository.save(location);
    }

    @Override
    public void delete(String id) {

        locationRepository.deleteById(id);
    }
}