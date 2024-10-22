package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.entity.Category;
import kz.oj.tinkoffhw5.mapper.CategoryMapper;
import kz.oj.tinkoffhw5.repository.CategoryRepository;
import kz.oj.tinkoffhw5.web.rest.v1.dto.CategoryDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void findAll() {

        // Given
        Category category = mock(Category.class);
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        CategoryDto dto = mock(CategoryDto.class);
        when(categoryMapper.toDto(category)).thenReturn(dto);

        // When
        List<CategoryDto> result = categoryService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void givenNonexistentId_whenFindById_thenThrow() {

        // Given
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // When, then
        assertThrows(IllegalArgumentException.class, () -> categoryService.findById(id));
    }

    @Test
    void findById() {

        // Given
        Category category = mock(Category.class);

        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryDto dto = mock(CategoryDto.class);
        when(categoryMapper.toDto(category)).thenReturn(dto);

        // When
        CategoryDto result = categoryService.findById(id);

        // Then
        assertEquals(dto, result);
    }

    @Test
    void create() {

        // Given
        CategoryCreateRequest request = mock(CategoryCreateRequest.class);
        when(request.getSlug()).thenReturn("ala");
        when(request.getName()).thenReturn("Алматы");

        Category category = mock(Category.class);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDto dto = mock(CategoryDto.class);
        when(categoryMapper.toDto(category)).thenReturn(dto);

        // When
        CategoryDto result = categoryService.create(request);

        // Then
        assertEquals(dto, result);

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());

        Category savedCategory = captor.getValue();
        assertEquals(request.getSlug(), savedCategory.getSlug());
        assertEquals(request.getName(), savedCategory.getName());
    }

    @Test
    void givenNonexistentId_whenUpdate_thenThrow() {

        // Given
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // When, then
        assertThrows(
                IllegalArgumentException.class,
                () -> categoryService.update(id, mock(CategoryUpdateRequest.class))
        );
    }

    @Test
    void givenOnlySlugSet_whenUpdate_onlySlugIsUpdated() {

        // Given
        Category category = mock(Category.class);

        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryUpdateRequest request = mock(CategoryUpdateRequest.class);
        when(request.getSlug()).thenReturn("ala");
        when(request.getName()).thenReturn(null);

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(mock(Category.class));

        // When
        categoryService.update(id, request);

        // Then
        verify(category).setSlug(request.getSlug());
        verify(categoryRepository).save(category);

        verifyNoMoreInteractions(category);
    }

    @Test
    void givenOnlyNameSet_whenUpdate_onlyNameIsUpdated() {

        // Given
        Category category = mock(Category.class);

        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryUpdateRequest request = mock(CategoryUpdateRequest.class);
        when(request.getSlug()).thenReturn(null);
        when(request.getName()).thenReturn("Алматы");

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(mock(Category.class));

        // When
        categoryService.update(id, request);

        // Then
        verify(category).setName(request.getName());
        verify(categoryRepository).save(category);

        verifyNoMoreInteractions(category);
    }

    @Test
    void update() {

        // Given
        Category category = mock(Category.class);

        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryUpdateRequest request = mock(CategoryUpdateRequest.class);
        when(request.getSlug()).thenReturn("ala");
        when(request.getName()).thenReturn("Алматы");

        Category savedCategory = mock(Category.class);
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        CategoryDto dto = mock(CategoryDto.class);
        when(categoryMapper.toDto(savedCategory)).thenReturn(dto);

        // When
        CategoryDto result = categoryService.update(id, request);

        // Then
        assertEquals(dto, result);

        verify(category).setSlug(request.getSlug());
        verify(category).setName(request.getName());
        verifyNoMoreInteractions(category);

        verify(categoryRepository).save(category);
    }


    @Test
    void delete() {

        // Given
        Long id = 1L;

        // When
        categoryService.delete(id);

        // Then
        verify(categoryRepository).deleteById(id);
    }
}