package kz.oj.tinkoffhw5.task;

import kz.oj.tinkoffhw5.integration.kudago.CategoryClient;
import kz.oj.tinkoffhw5.service.CategoryService;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitializeCategoriesTask implements CommandLineRunner {

    private final CategoryClient categoryClient;
    private final CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {

        log.info("Task started...");

        categoryClient.findAll().stream().map(category -> {

            @SuppressWarnings("all")
            CategoryCreateRequest request = CategoryCreateRequest.builder()
                    .slug(category.getSlug())
                    .name(category.getName())
                    .build();

            return request;

        }).forEach(categoryService::create);

        log.info("Task finished...");
    }
}
