package kz.oj.tinkoffhw5.task;

import kz.oj.tinkoffhw5.integration.kudago.LocationClient;
import kz.oj.tinkoffhw5.service.LocationService;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitializeLocationsTask implements CommandLineRunner {

    private final LocationClient locationClient;
    private final LocationService locationService;

    @Override
    public void run(String... args) throws Exception {

        log.info("Task started...");

        locationClient.findAll().stream().map(location -> {

            @SuppressWarnings("all")
            LocationCreateRequest request = LocationCreateRequest.builder()
                    .slug(location.getSlug())
                    .name(location.getName())
                    .build();

            return request;

        }).forEach(locationService::create);

        log.info("Task finished...");
    }
}
