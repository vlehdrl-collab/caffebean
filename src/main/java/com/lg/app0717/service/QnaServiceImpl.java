package com.lg.app0717.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lg.app0717.domain.Qna;

import com.lg.app0717.repository.QnaRepository;

@Service
public class QnaServiceImpl implements QnaService {

    @Autowired
    private QnaRepository qnaRepo;

  

    @Override
    public Page<Qna> getQnaList(Pageable pageable) {
        return qnaRepo.findAll(pageable);
    }

    @Override
    public Qna getQna(Long id) {
        Qna qna = qnaRepo.findById(id).orElse(null);
        if (qna != null) {
            qna.setViewCount(qna.getViewCount() + 1);
            qnaRepo.save(qna);
        }
        return qna;
    }

    @Override
    public void insertQna(Qna qna) {
        qnaRepo.save(qna);
    }

    @Override
    public void updateQna(Long id, Qna qna) {
        Qna existing = qnaRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setTitle(qna.getTitle());
            existing.setContent(qna.getContent());
            existing.setWriter(qna.getWriter());
            existing.setFileOriginalName(qna.getFileOriginalName());
            existing.setFileStoredName(qna.getFileStoredName());
            qnaRepo.save(existing);
        }
    }

    @Override
    public void deleteQna(Long id) {
        qnaRepo.deleteById(id);
    }

    // 검색 기능
    @Override
    public Page<Qna> searchQnaByTitle(String keyword, Pageable pageable) {
        return qnaRepo.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Qna> searchQnaByContent(String keyword, Pageable pageable) {
        return qnaRepo.findByContentContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Qna> searchQnaByWriter(String keyword, Pageable pageable) {
        return qnaRepo.findByWriterContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Qna> searchQnaByTitleOrContent(String keyword, Pageable pageable) {
        return qnaRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);
    }

    
}