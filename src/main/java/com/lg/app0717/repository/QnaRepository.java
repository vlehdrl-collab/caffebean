package com.lg.app0717.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lg.app0717.domain.Qna;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    Page<Qna> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Qna> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Qna> findByWriterContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Qna> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String keyword1, String keyword2, Pageable pageable);
}