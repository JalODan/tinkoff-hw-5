package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.entity.Category;
import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.repository.CategoryRepository;
import kz.oj.tinkoffhw5.service.CategoryService;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryUpdateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена по id=" + id));
    }

    @Override
    public void create(CategoryCreateRequest request) {

        Category category = Category.builder()
                .slug(request.getSlug())
                .name(request.getName())
                .build();

        categoryRepository.save(category);
    }

    @Override
    public void update(Long id, CategoryUpdateRequest request) {

        Category category = findById(id);
        category.setSlug(request.getSlug());
        category.setName(request.getName());

        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {

        categoryRepository.deleteById(id);
    }
}
