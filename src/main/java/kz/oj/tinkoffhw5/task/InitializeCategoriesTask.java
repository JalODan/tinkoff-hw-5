package kz.oj.tinkoffhw5.task;

import kz.oj.tinkoffhw5.aop.Timed;
import kz.oj.tinkoffhw5.service.CategoryInitializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@RequiredArgsConstructor
@Slf4j
public class InitializeCategoriesTask implements CommandLineRunner {

    private final CategoryInitializationService categoryInitializationService;

    @Override
    @Timed
    public void run(String... args) {

        log.info("Task started...");
        categoryInitializationService.initializeCategories();
        log.info("Task finished...");
    }
}
