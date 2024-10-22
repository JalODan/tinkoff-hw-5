package kz.oj.tinkoffhw5.web.rest.v1.controller;

import kz.oj.tinkoffhw5.aop.Timed;
import kz.oj.tinkoffhw5.entity.Category;
import kz.oj.tinkoffhw5.service.CategoryService;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.CategoryUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
@Timed
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {

        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryCreateRequest request) {

        return ResponseEntity.ok(categoryService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody CategoryUpdateRequest request) {

        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

}
