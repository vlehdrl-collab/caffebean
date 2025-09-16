package com.lg.app0717.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lg.app0717.domain.Notice;
import com.lg.app0717.domain.Person;
import com.lg.app0717.domain.Qna;
import com.lg.app0717.repository.BoardRepository;
import com.lg.app0717.repository.NoticeRepository;

@Service
public class BoardServiceImp implements BoardService {
	@Autowired
	private BoardRepository br;
	
	@Override
	public List<Person> getBoardList() {
		// DAO에 접근해서 DB의 데이터를 가져옴
		List<Person> list = (List<Person>)br.findAll();
		return list;
	}

	@Override
	public void insertPerson(Person p) {
		// TODO Auto-generated method stub
		br.save(p);
	}

	@Override
	public Person getPersonBySeq(Long seq) {
		// TODO Auto-generated method stub
		return br.findById(seq).get();
	}

	@Override
	public void delPersonBySeq(Long seq) {
		// TODO Auto-generated method stub
		br.deleteById(seq);
	}

	@Override
	public void updatePerson(Person p) {
		// TODO Auto-generated method stub
		br.save(p);
	}
	
    @Autowired
    private NoticeRepository noticeRepo;

    @Override
    public List<Notice> getNoticeList() {
        // 작성일(createdDate) 내림차순 정렬
        return noticeRepo.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
    }

    @Override
    public Notice getNotice(Long id) {
        Notice n = noticeRepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("해당 공지사항이 없습니다. id=" + id));
        // 조회수 증가
        n.setViewCount(n.getViewCount() + 1);
        return noticeRepo.save(n);
    }

    @Override
    public void insertNotice(Notice notice) {
        noticeRepo.save(notice);
    }
    
    @Override
    public void deleteNotice(Long id) {
        noticeRepo.deleteById(id);
    }
    
    @Override
    public Page<Notice> getNoticeList(Pageable pageable) {
        return noticeRepo.findAll(pageable);
    }
    
    @Override
    public void updateNotice(Long id, Notice updatedNotice) {
        Notice n = noticeRepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("공지 없음: " + id));
        //  수정할 필드만 덮어쓰기
        n.setTitle(updatedNotice.getTitle());
        n.setContent(updatedNotice.getContent());
        // (파일 교체 기능이 필요하다면, 파일 처리 로직도 여기에 추가)
        noticeRepo.save(n);
    }


    
    
}
