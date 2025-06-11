package com.codify.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "ai_conversations")
public class AiConversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private Long conversationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @NotBlank
    @Column(name = "ai_response", nullable = false, columnDefinition = "TEXT")
    private String aiResponse;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    @Column(name = "tech_category", length = 100)
    private String techCategory;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    public AiConversation() {}

    public AiConversation(User user, String question, String aiResponse, QuestionType questionType) {
        this.user = user;
        this.question = question;
        this.aiResponse = aiResponse;
        this.questionType = questionType;
    }

    public AiConversation(User user, String question, String aiResponse,
                          QuestionType questionType, String techCategory) {
        this.user = user;
        this.question = question;
        this.aiResponse = aiResponse;
        this.questionType = questionType;
        this.techCategory = techCategory;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getTechCategory() {
        return techCategory;
    }

    public void setTechCategory(String techCategory) {
        this.techCategory = techCategory;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AiConversation{" +
                "conversationId=" + conversationId +
                ", user=" + (user != null ? user.getNickname() : null) +
                ", questionType=" + questionType +
                ", techCategory='" + techCategory + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}