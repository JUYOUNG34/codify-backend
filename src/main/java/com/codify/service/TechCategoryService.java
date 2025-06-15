package com.codify.service;

import com.codify.entity.CategoryType;
import com.codify.entity.TechCategory;
import com.codify.repository.TechCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TechCategoryService {

    @Autowired
    private TechCategoryRepository techCategoryRepository;

    public List<TechCategory> getAllCategories() {
        return techCategoryRepository.findByOrderByCategoryNameAsc();
    }

    public TechCategory createCategory(String categoryName, CategoryType categoryType , String githubTopicName) {
        if (techCategoryRepository.existsByCategoryName(categoryName)) {
            throw new IllegalArgumentException("이미 존재하는 기술 카테고리입니다");
        }
        TechCategory techCategory = new TechCategory(categoryName, categoryType, githubTopicName);
        return techCategoryRepository.save(techCategory);
    }

    public Optional<TechCategory> getCategoryById(Long id) {
        return techCategoryRepository.findById(id);
    }

    public Optional<TechCategory> getCategoryByName(String name) {
        return techCategoryRepository.findByCategoryName(name);
    }

}
