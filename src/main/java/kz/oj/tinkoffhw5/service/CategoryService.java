package kz.oj.tinkoffhw5.service;

import kz.oj.tinkoffhw5.web.rest.v1.dto.CategoryDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAll();

    CategoryDto findById(Long id);

    CategoryDto create(CategoryCreateRequest request);

    CategoryDto update(Long id, CategoryUpdateRequest request);

    void delete(Long id);
}
