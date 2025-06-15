package com.codify.repository;

import com.codify.entity.CategoryType;
import com.codify.entity.TechCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechCategoryRepository extends JpaRepository<TechCategory, Long> {

    Optional<TechCategory> findByCategoryName(String categoryName);

    List<TechCategory> findByCategoryType(CategoryType categoryType);

    boolean existsByCategoryName(String categoryName);
    List<TechCategory> findByOrderByCategoryNameAsc();

    @Query("select tc from TechCategory tc where tc.isTrending = true order by tc.categoryName")
    List<TechCategory> findByTrendTechCategory();

    @Query("select tc from TechCategory tc where tc.categoryName like %:keyword% order by tc.categoryName")
    List<TechCategory> searchTechCategory(@Param("keyword") String keyword);

    @Query("select tc from TechCategory tc where tc.githubTopicName is not null order by tc.categoryName")
    List<TechCategory> findGithubTechCategory();
}