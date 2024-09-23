package kz.oj.tinkoffhw5.service;

import kz.oj.tinkoffhw5.entity.Category;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    void create(CategoryCreateRequest request);

    void update(Long id, CategoryUpdateRequest request);

    void delete(Long id);
}
