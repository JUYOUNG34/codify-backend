package com.codify.repository;

import com.codify.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    // 저장된 파일명으로 조회 (기본 메서드만)
    Optional<File> findByStoredName(String storedName);
}