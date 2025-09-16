package com.lg.app0717.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.lg.app0717.domain.Notice;
import com.lg.app0717.domain.Person;
import com.lg.app0717.domain.PersonTest;
import com.lg.app0717.domain.Qna;
import com.lg.app0717.domain.Student;
import com.lg.app0717.service.BoardService;
import com.lg.app0717.service.FileStorageService;
import com.lg.app0717.service.QnaService;
import com.lg.app0717.service.StudentService;
import com.lg.app0717.util.RandData;

// MVC 아키텍처(구조) 패턴
// M:Model, V:View, C:Controller
// 자바 어노테이션: 자바 컴파일러에 정확한 정보를 전달
@Controller
public class BoardController {
	@Autowired
	private BoardService bs;

	@Autowired
	private StudentService ss;

	@Autowired
	private FileStorageService fs;
	
	@Autowired
	private QnaService qs;

	@GetMapping("/getBoard")
	public String getBoard(Model model) {
		System.out.println("프론트에서 요청!");
		// Person 클래스 객체를 생성하여 model 객체에 담아서 view에 전달
		PersonTest p = new PersonTest(1L, "홍길동", "010-1234-1234", "조선 한양 홍대감댁");
		model.addAttribute("person", p);
		return "board/showBoard";
	}

	@GetMapping("/getBoardList")
	public String getBoardList(Model model) {
		System.out.println("프론트에서 요청!");
		// Person 클래스 객체를 생성하여 model 객체에 담아서 view에 전달
		PersonTest p = new PersonTest(1L, "홍길동", "010-1234-1234", "조선 한양 홍대감댁");
		PersonTest p1 = new PersonTest(2L, "전우치", "010-1234-1111", "강원 두메산골");
		PersonTest p2 = new PersonTest(3L, "유관순", "010-1234-2222", "서울 종로");
		ArrayList<PersonTest> list = new ArrayList<>();
		list.add(p);
		list.add(p1);
		list.add(p2);
		model.addAttribute("personList", list);
		return "board/showBoardList";
	}

	@GetMapping("/getBoardRandList")
	public String getBoardRandList(Model model) {
		ArrayList<PersonTest> list = new ArrayList<>();
		// 랜덤 객체 생성하여 리스트에 저장
		for (long i = 0; i < 10; i++) {
			list.add(new PersonTest(i + 1, RandData.getName(), RandData.getTel(), RandData.getAddr()));
		}

		model.addAttribute("randList", list);
		return "board/showBoardRandList";
	}

	@GetMapping("/getBoardDbList")
	public String getBoardDbList(Model model) {
		List<Person> list = new ArrayList<>();
		// DB에서 데이터 가져오기
		list = bs.getBoardList();
		model.addAttribute("dbList", list);
		return "person/showBoardDbList";
	}

	@GetMapping("/getStdDbList")
	public String getStdDbList(Model model) {
		List<Student> list = new ArrayList<>();
		// DB에서 데이터 가져오기
		list = ss.getStdList();
		model.addAttribute("stdList", list);
		return "student/showStdDbList";
	}

	@GetMapping("/insPerForm")
	public String insertPersonForm() {
		return "person/insPerForm";
	}

	@PostMapping("/insertPerson")
	public String insertPerson(@ModelAttribute Person p) {
		// 폼태그에서 받은 정보를 DB에 저장
		System.out.println("이름:" + p.getName());
		System.out.println("카카오ID:" + p.getKakao());
		bs.insertPerson(p);
		return "redirect:/getBoardDbList";
	}

	@GetMapping("/showPersonDetail/{seq}")
	public String showPersonDetail(@PathVariable("seq") Long seq, Model model) {
		Person p = bs.getPersonBySeq(seq);
		System.out.println("이름:" + p.getName());
		System.out.println("이메일:" + p.getEmail());
		model.addAttribute("personDetail", p);
		return "person/showPerDetail";
	}

	@GetMapping("/deletePerson/{seq}")
	public String deletePerson(@PathVariable("seq") Long seq, Model model) {
		bs.delPersonBySeq(seq);
		return "redirect:/getBoardDbList";
	}

	@GetMapping("/editPerForm/{seq}")
	public String editPersonForm(@PathVariable("seq") Long seq, Model model) {
		Person p = bs.getPersonBySeq(seq);
		model.addAttribute("person", p);
		return "person/editPerForm";
	}

	@PostMapping("/updatePerson")
	public String updatePerson(@ModelAttribute Person p) {
		bs.updatePerson(p);
		return "redirect:/getBoardDbList";
	}

	@GetMapping("/cafe/{menu}")
	public String cafeMenuRouter(@PathVariable("menu") String menu) {
		String ret = "";
		if (menu.equals("index"))
			ret = "index";
		else if (menu.equals("about"))
			ret = "about";
		else if (menu.equals("location"))
			ret = "location";
		else if (menu.equals("coffee"))
			ret = "coffee";
		else if (menu.equals("bakery"))
			ret = "bakery";
		return "cafe/" + ret;
	}
	
	@GetMapping("/cafe")
	public String cafeRoot() {
	    return "cafe/index"; // 또는 기본 페이지
	}

	// 1) 공지사항 목록
	@GetMapping("/notices")
	public String noticeList(Model model, 
	        @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
	    Page<Notice> noticePage = bs.getNoticeList(pageable);
	    model.addAttribute("noticePage", noticePage);
	    return "board/showNoticeList";
	}

	// 2) 상세 보기 (조회수 1 증가)
	@GetMapping("/notices/{id}")
	public String noticeDetail(@PathVariable("id") Long id, Model model) {
		Notice notice = bs.getNotice(id);
		model.addAttribute("notice", notice);
		return "board/showNoticeDetail";
	}

	// 3) 새 글 작성 폼
	@GetMapping("/notices/new")
	public String noticeForm(Model model) {
		model.addAttribute("notice", new Notice());
		return "board/noticeForm";
	}

	// 4) 새 글 등록 처리 (파일 업로드 포함)
	@PostMapping(value = "/notices", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String noticeCreate(@ModelAttribute Notice notice, @RequestParam("file") MultipartFile file) {
		// 파일이 비어있지 않으면 저장
		if (file != null && !file.isEmpty()) {
			String storedName = fs.storeFile(file);
			notice.setFileOriginalName(file.getOriginalFilename());
			notice.setFileStoredName(storedName);
		}
		bs.insertNotice(notice);
		return "redirect:/notices";
	}

	// 5) 첨부파일 다운로드
	@GetMapping("/notices/files/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
		Resource resource = fs.loadFileAsResource(fileName);
		String header = "attachment; filename=\"" + resource.getFilename() + "\"";
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, header).body(resource);
	}

	// 6) 삭제
	@GetMapping("/notices/delete/{id}")
	public String noticeDelete(@PathVariable("id") Long id) {
		bs.deleteNotice(id);
		return "redirect:/notices";
	}
	
	@GetMapping("/notices/edit/{id}")
	public String noticeEditForm(@PathVariable("id") Long id, Model model) {
	    Notice notice = bs.getNotice(id);
	    model.addAttribute("notice", notice);
	    return "board/noticeForm";  // 작성 폼과 동일한 템플릿 재사용
	}

	/// 수정 처리
	@PostMapping(value = "/notices/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String noticeEdit(
	        @PathVariable("id") Long id,
	        @ModelAttribute Notice notice,
	        @RequestParam("file") MultipartFile file
	) {
	    // (선택) 새 파일 업로드 시 기존 파일 대체 로직
	    if (!file.isEmpty()) {
	        String stored = fs.storeFile(file);
	        notice.setFileOriginalName(file.getOriginalFilename());
	        notice.setFileStoredName(stored);
	    }
	    bs.updateNotice(id, notice);
	    return "redirect:/notices/" + id;
	}
	// 1) Q&A 목록
	@GetMapping("/qna")
	public String qnaList(Model model,
			@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Qna> qnaPage = qs.getQnaList(pageable);
		model.addAttribute("qnaPage", qnaPage);
		return "board/showQnaList";
	}
	
	// 2) Q&A 상세보기 (조회수 증가)
	@GetMapping("/qna/{id}")
	public String qnaDetail(@PathVariable("id") Long id, Model model) {
	    Qna qna = qs.getQna(id);
	    model.addAttribute("qna", qna);
	    return "board/showQnaDetail";
	}
	// 3) 새 질문 작성 폼
	@GetMapping("/qna/new")
	public String qnaForm(Model model) {
		model.addAttribute("qna", new Qna());
		return "board/qnaForm";
	}
	
	// 4) 새 질문 등록 처리 (파일 업로드 포함)
	@PostMapping(value = "/qna", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String qnaCreate(@ModelAttribute Qna qna, @RequestParam("file") MultipartFile file) {
		// 파일이 비어있지 않으면 저장
		if (file != null && !file.isEmpty()) {
			String storedName = fs.storeFile(file);
			qna.setFileOriginalName(file.getOriginalFilename());
			qna.setFileStoredName(storedName);
		}
		qs.insertQna(qna);
		return "redirect:/qna";
	}
	
	// 5) Q&A 수정 폼
	@GetMapping("/qna/edit/{id}")
	public String qnaEditForm(@PathVariable("id") Long id, Model model) {
		Qna qna = qs.getQna(id);
		model.addAttribute("qna", qna);
		return "board/qnaForm";
	}
	
	// 6) Q&A 수정 처리
	@PostMapping(value = "/qna/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String qnaEdit(
			@PathVariable("id") Long id,
			@ModelAttribute Qna qna,
			@RequestParam("file") MultipartFile file) {
		// 새 파일 업로드 시 기존 파일 대체
		if (!file.isEmpty()) {
			String stored = fs.storeFile(file);
			qna.setFileOriginalName(file.getOriginalFilename());
			qna.setFileStoredName(stored);
		}
		qs.updateQna(id, qna);
		return "redirect:/qna/" + id;
	}
	
	// 7) Q&A 삭제
	@GetMapping("/qna/delete/{id}")
	public String qnaDelete(@PathVariable("id") Long id) {
		qs.deleteQna(id);
		return "redirect:/qna";
	}
	
	// 8) Q&A 검색
	@GetMapping("/qna/search")
	public String qnaSearch(
			@RequestParam(value = "searchType", defaultValue = "title") String searchType,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		
		Page<Qna> qnaPage;
		
		// 검색어가 비어있으면 전체 목록
		if (keyword.trim().isEmpty()) {
			qnaPage = qs.getQnaList(pageable);
		} else {
			// 검색 타입에 따라 다른 검색 수행
			switch (searchType) {
				case "title":
					qnaPage = qs.searchQnaByTitle(keyword, pageable);
					break;
				case "content":
					qnaPage = qs.searchQnaByContent(keyword, pageable);
					break;
				case "writer":
					qnaPage = qs.searchQnaByWriter(keyword, pageable);
					break;
				case "titleContent":
					qnaPage = qs.searchQnaByTitleOrContent(keyword, pageable);
					break;
				default:
					qnaPage = qs.getQnaList(pageable);
			}
		}
		
		model.addAttribute("qnaPage", qnaPage);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		
		return "board/showQnaList";
	}
	
	
	// 13) Q&A 첨부파일 다운로드
	@GetMapping("/qna/files/{fileName:.+}")
	public ResponseEntity<Resource> downloadQnaFile(@PathVariable("fileName") String fileName) {
		Resource resource = fs.loadFileAsResource(fileName);
		String header = "attachment; filename=\"" + resource.getFilename() + "\"";
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, header).body(resource);
	}
}

