package kz.oj.tinkoffhw5.task;

import kz.oj.tinkoffhw5.aop.Timed;
import kz.oj.tinkoffhw5.service.LocationInitializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Timed
@Profile("!test")
public class InitializeLocationsTask implements CommandLineRunner {

    private final LocationInitializationService locationInitializationService;

    @Override
    public void run(String... args) throws Exception {

        log.info("Task started...");
        locationInitializationService.initializeLocations();
        log.info("Task finished...");
    }
}
