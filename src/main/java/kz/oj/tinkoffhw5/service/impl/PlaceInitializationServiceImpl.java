package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.integration.kudago.LocationClient;
import kz.oj.tinkoffhw5.repository.PlaceRepository;
import kz.oj.tinkoffhw5.service.PlaceInitializationService;
import kz.oj.tinkoffhw5.service.PlaceService;
import kz.oj.tinkoffhw5.web.rest.v1.request.PlaceCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceInitializationServiceImpl implements PlaceInitializationService {

    private final LocationClient locationClient;
    private final PlaceService placeService;
    private final PlaceRepository placeRepository;

    @Override
    public void initializePlaces() {

        if (placeRepository.count() > 0) {
            return;
        }

        locationClient.findAll().stream().map(location -> {

            @SuppressWarnings("all")
            PlaceCreateRequest request = PlaceCreateRequest.builder()
                    .slug(location.getSlug())
                    .name(location.getName())
                    .build();

            return request;

        }).forEach(placeService::create);
    }
}
