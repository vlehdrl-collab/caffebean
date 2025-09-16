package com.lg.app0717.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lg.app0717.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}