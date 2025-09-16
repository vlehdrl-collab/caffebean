package com.lg.app0717.service;

import java.util.List;

import com.lg.app0717.domain.Notice;
import com.lg.app0717.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.lg.app0717.domain.Notice;

import com.lg.app0717.domain.Qna;


public interface BoardService {
	List<Person> getBoardList();
	void insertPerson(Person p);
	Person getPersonBySeq(Long seq);
	void delPersonBySeq(Long seq);
	void updatePerson(Person p);
    List<Notice> getNoticeList();
    Notice getNotice(Long id);
    void insertNotice(Notice notice);
    void deleteNotice(Long id);
    Page<Notice> getNoticeList(Pageable pageable);
    void updateNotice(Long id, Notice notice);
    
   
}
