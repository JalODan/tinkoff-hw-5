package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.entity.Category;
import kz.oj.tinkoffhw5.mapper.CategoryMapper;
import kz.oj.tinkoffhw5.repository.CategoryRepository;
import kz.oj.tinkoffhw5.service.CategoryService;
import kz.oj.tinkoffhw5.web.rest.v1.dto.CategoryDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto findById(Long id) {

        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена по id=" + id));
    }

    @Override
    public CategoryDto create(CategoryCreateRequest request) {

        Category category = Category.builder()
                .slug(request.getSlug())
                .name(request.getName())
                .build();

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CategoryUpdateRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена по id=" + id));

        if (request.getSlug() != null) {
            category.setSlug(request.getSlug());
        }

        if (request.getName() != null) {
            category.setName(request.getName());
        }

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {

        categoryRepository.deleteById(id);
    }
}
