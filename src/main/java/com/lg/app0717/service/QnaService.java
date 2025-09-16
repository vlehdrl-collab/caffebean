package com.lg.app0717.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.lg.app0717.domain.Qna;

public interface QnaService {

    // 목록
    Page<Qna> getQnaList(Pageable pageable);

    // 상세 조회
    Qna getQna(Long id);

    // 작성
    void insertQna(Qna qna);

    // 수정
    void updateQna(Long id, Qna qna);

    // 삭제
    void deleteQna(Long id);

    // 검색
    Page<Qna> searchQnaByTitle(String keyword, Pageable pageable);
    Page<Qna> searchQnaByContent(String keyword, Pageable pageable);
    Page<Qna> searchQnaByWriter(String keyword, Pageable pageable);
    Page<Qna> searchQnaByTitleOrContent(String keyword, Pageable pageable);

    
}