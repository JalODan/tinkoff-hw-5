package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.integration.kudago.CategoryClient;
import kz.oj.tinkoffhw5.repository.CategoryRepository;
import kz.oj.tinkoffhw5.service.CategoryInitializationService;
import kz.oj.tinkoffhw5.service.CategoryService;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryInitializationServiceImpl implements CategoryInitializationService {

    private final CategoryClient categoryClient;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Override
    public void initializeCategories() {

        if (categoryRepository.count() > 0) {
            return;
        }

        categoryClient.findAll().stream().map(category -> {

            @SuppressWarnings("all")
            CategoryCreateRequest request = CategoryCreateRequest.builder()
                    .slug(category.getSlug())
                    .name(category.getName())
                    .build();

            return request;

        }).forEach(categoryService::create);
    }
}
