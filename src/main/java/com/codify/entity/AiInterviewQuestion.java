package com.codify.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "ai_interview_questions")
public class AiInterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech_category_id")
    private TechCategory techCategory;

    @NotBlank
    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false)
    private DifficultyLevel difficultyLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private InterviewQuestionType questionType;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    public AiInterviewQuestion() {}

    public AiInterviewQuestion(String questionText, DifficultyLevel difficultyLevel,
                               InterviewQuestionType questionType) {
        this.questionText = questionText;
        this.difficultyLevel = difficultyLevel;
        this.questionType = questionType;
    }

    public AiInterviewQuestion(TechCategory techCategory, String questionText,
                               DifficultyLevel difficultyLevel, InterviewQuestionType questionType) {
        this.techCategory = techCategory;
        this.questionText = questionText;
        this.difficultyLevel = difficultyLevel;
        this.questionType = questionType;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public TechCategory getTechCategory() {
        return techCategory;
    }

    public void setTechCategory(TechCategory techCategory) {
        this.techCategory = techCategory;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public InterviewQuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(InterviewQuestionType questionType) {
        this.questionType = questionType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }

    @Override
    public String toString() {
        return "AiInterviewQuestion{" +
                "questionId=" + questionId +
                ", techCategory=" + (techCategory != null ? techCategory.getCategoryName() : null) +
                ", difficultyLevel=" + difficultyLevel +
                ", questionType=" + questionType +
                ", isActive=" + isActive +
                '}';
    }
}