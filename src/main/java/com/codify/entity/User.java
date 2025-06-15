package com.codify.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // 이 줄 추가
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "tech_stacks", columnDefinition = "text[]")
    private String[] techStacks;

    @Enumerated(EnumType.STRING)
    @Column(name = "career_level")
    private CareerLevel careerLevel = CareerLevel.JUNIOR;

    @Column(name = "github_username", length = 100)
    private String githubUsername;

    @Column(name = "portfolio_url", length = 500)
    private String portfolioUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column(name = "oauth_provider", length = 20)
    private String oauthProvider;

    @Column(name = "oauth_id", length = 100)
    private String oauthId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    public User() {}

    public User(String email, String nickname, String passwordHash) {
        this.email = email;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
    }

    public User(String email, String nickname, String oauthProvider, String oauthId) {
        this.email = email;
        this.nickname = nickname;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
    }

    // getters and setters (기존과 동일)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String[] getTechStacks() {
        return techStacks;
    }

    public void setTechStacks(String[] techStacks) {
        this.techStacks = techStacks;
    }

    public CareerLevel getCareerLevel() {
        return careerLevel;
    }

    public void setCareerLevel(CareerLevel careerLevel) {
        this.careerLevel = careerLevel;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isOAuthUser() {
        return oauthProvider != null && oauthId != null;
    }

    public boolean isLocalUser() {
        return oauthProvider == null || "LOCAL".equals(oauthProvider);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", careerLevel=" + careerLevel +
                ", isActive=" + isActive +
                '}';
    }
}