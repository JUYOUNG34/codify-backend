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

    @Query("SELECT u FROM User u WHERE u.nickname LIKE %:keyword% OR u.bio LIKE %:keyword%")
    List<User> searchByKeyword(@Param("keyword") String keyword);

    List<User> findByCareerLevel(@Param("careerLevel") String careerLevel);

    @Query(value = "SELECT * FROM users u WHERE :techStack = ANY(u.tech_stacks)", nativeQuery = true)
    List<User> findByTechStack(@Param("techStack") String techStack);
}