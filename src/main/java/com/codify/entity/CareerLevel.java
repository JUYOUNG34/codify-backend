package com.codify.entity;

public enum CareerLevel {
    JUNIOR("주니어"),
    MIDDLE("미들"),
    SENIOR("시니어"),
    LEAD("리드"),
    PRINCIPAL("프린시펄");

    private final String koreanName;

    CareerLevel(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static CareerLevel fromString(String value) {
        if (value == null) {
            return JUNIOR;
        }

        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return JUNIOR;
        }
    }
}