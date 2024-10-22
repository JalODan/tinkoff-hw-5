package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.integration.kudago.LocationClient;
import kz.oj.tinkoffhw5.repository.LocationRepository;
import kz.oj.tinkoffhw5.service.LocationInitializationService;
import kz.oj.tinkoffhw5.service.LocationService;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationInitializationServiceImpl implements LocationInitializationService {

    private final LocationClient locationClient;
    private final LocationService locationService;
    private final LocationRepository locationRepository;

    @Override
    public void initializeLocations() {

        if (locationRepository.count() > 0) {
            return;
        }

        locationClient.findAll().stream().map(location -> {

            @SuppressWarnings("all")
            LocationCreateRequest request = LocationCreateRequest.builder()
                    .slug(location.getSlug())
                    .name(location.getName())
                    .build();

            return request;

        }).forEach(locationService::create);
    }
}
