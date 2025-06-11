package com.codify.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "tech_categories")
public class TechCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "category_name", unique = true, nullable = false)
    private String categoryName;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private CategoryType categoryType;

    @Column(name = "github_topic_name", length = 100)
    private String githubTopicName;

    @Column(name = "is_trending")
    private Boolean isTrending = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "techCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatRoom> chatRooms;

    @OneToMany(mappedBy = "techCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AiInterviewQuestion> interviewQuestions;

    public TechCategory() {}

    public TechCategory(String categoryName, CategoryType categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public TechCategory(String categoryName, CategoryType categoryType, String githubTopicName) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.githubTopicName = githubTopicName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

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

    public Boolean getIsTrending() {
        return isTrending;
    }

    public void setIsTrending(Boolean isTrending) {
        this.isTrending = isTrending;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public List<AiInterviewQuestion> getInterviewQuestions() {
        return interviewQuestions;
    }

    public void setInterviewQuestions(List<AiInterviewQuestion> interviewQuestions) {
        this.interviewQuestions = interviewQuestions;
    }

    @Override
    public String toString() {
        return "TechCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                ", isTrending=" + isTrending +
                '}';
    }
}