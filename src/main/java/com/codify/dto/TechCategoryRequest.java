package com.codify.dto;

import com.codify.entity.CategoryType;

public class TechCategoryRequest {
    private String categoryName;
    private CategoryType categoryType;
    private String githubTopicName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public String getGithubTopicName() {
        return githubTopicName;
    }

    public void setGithubTopicName(String githubTopicName) {
        this.githubTopicName = githubTopicName;
    }

    public TechCategoryRequest(){}

    public TechCategoryRequest(String categoryName, CategoryType categoryType, String githubTopicName) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.githubTopicName = githubTopicName;
    }
}
