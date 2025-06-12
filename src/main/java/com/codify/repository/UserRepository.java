package com.codify.repository;

import com.codify.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<User> finbyOauthProviderAndOauthId(String oauthProvider, String oauthId);

    List<User> findByIsActiveTrue();
    List<User> findByEmailVerifiedTure();

    @Query("SELECT u from User u where u.nickname like %:keyword% or u.bio like %:keyword%")
    List<User> searchByKeyword(@Param("keyword") String keyword);

    List<User> findByCareerLevel(@Param("careerLevel") String careerLevel);

    @Query("SELECT u from User u where :techStack = any(u.techStacks)")
    List<User> findByTechStack(@Param("techStacks") String techStacks);
}
