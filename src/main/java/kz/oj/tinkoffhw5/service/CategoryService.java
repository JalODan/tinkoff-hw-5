package kz.oj.tinkoffhw5.service;

import kz.oj.tinkoffhw5.entity.Category;
import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category create(CategoryCreateRequest request);

    Category update(Long id, CategoryUpdateRequest request);

    void delete(Long id);
}
