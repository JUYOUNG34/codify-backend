package com.codify.repository;


import com.codify.entity.AiInterviewQuestion;
import com.codify.entity.DifficultyLevel;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiInterviewQuestionRepository {
    List<AiInterviewQuestion> findTechCategoryIdAndIsActiveTrue(@Param("categoryId") Long categoryId);
    List<AiInterviewQuestion> findByDifficultyLevelAndIsActiveTrue(@Param("difficultyLevel")DifficultyLevel difficultyLevel);
    List<AiInterviewQuestion> findByTechCategoryAndDifficulty(@Param("categoryId") Long categoryId, @Param("difficultyLevel") DifficultyLevel difficultyLevel);
    List<AiInterviewQuestion> searchByQuestionText(@Param("keyword") String keyword);
    List<AiInterviewQuestion> findRandomQuestions(@Param("limit") int limit);
    Long countByTechCategoryId(@Param("categoryId") Long categoryId);

}
