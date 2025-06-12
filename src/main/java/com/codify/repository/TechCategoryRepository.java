package com.codify.repository;

import com.codify.entity.TechCategory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechCategoryRepository {

    Optional<TechCategory> findByCategoryName(String categoryName);
    List<TechCategory> findCategoryType(String categoryType);
    List<TechCategory> findByTrendTechCategory();
    boolean existsByCategoryName(String categoryName);
    List<TechCategory> searchByCategoryName(@Param("keyword") String keyword);
    List<TechCategory> findGithubTechCategory();
    List<TechCategory> findByOrderByCategoryNameAsc();

}
