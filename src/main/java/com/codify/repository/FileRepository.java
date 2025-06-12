package com.codify.repository;

import com.codify.entity.File;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository {

    Optional<File> findByStoredName(String storedName);
    List<File> findByUploadedByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    List<File> findImageFiles();
    List<File> findDocumentFiles();
    List<File> searchByName(@Param("keyword") String keyword);
}
