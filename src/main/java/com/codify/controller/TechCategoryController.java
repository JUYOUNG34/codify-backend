package com.codify.controller;

import com.codify.dto.TechCategoryRequest;
import com.codify.entity.TechCategory;
import com.codify.service.TechCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tech-categories")
public class TechCategoryController {

    @Autowired
    private TechCategoryService techCategoryService;

    @GetMapping
    public ResponseEntity<List<TechCategory>> getAllTechCategories() {
        List<TechCategory> categories = techCategoryService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechCategory> getTechCategoryById(@PathVariable Long id) {
        Optional<TechCategory> category = techCategoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TechCategory> createTechCategory(@RequestBody TechCategoryRequest request) {  // 여기 수정
        try {
            TechCategory category = techCategoryService.createCategory(
                    request.getCategoryName(),
                    request.getCategoryType(),
                    request.getGithubTopicName()
            );
            return ResponseEntity.ok().body(category);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}