package com.codify.repository;

import com.codify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<User> findByOauthProviderAndOauthId(String oauthProvider, String oauthId);

    List<User> findByIsActiveTrue();
    List<User> findByEmailVerifiedTrue();

    @Query("SELECT u From User u where u.nickname like %:keyword% or u.bio like %:keyword%")
    List<User> searchByKeyword(@Param("keyword") String keyword);

    List<User> findByCareerLevel(@Param("careerLevel") String careerLevel);

    @Query("SELECT u From User u where :techStack = any(u.techStacks)")

    List<User> findByTechStack(@Param("techStacks") String techStacks);
}
