package com.lg.app0717.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lg.app0717.domain.Person;
import com.lg.app0717.domain.Student;
import com.lg.app0717.service.BoardService;
import com.lg.app0717.service.StudentService;

@Controller
public class StdController {
	@Autowired
	private StudentService ss;

	@GetMapping("/insStdForm")
	public String insertStudentForm() {
		return "student/insStdForm";
	}

	@PostMapping("/insertStudent")
	public String insertPerson(@ModelAttribute Student s) {
		// 폼태그에서 받은 정보를 DB에 저장
		System.out.println("이름:" + s.getName());
		System.out.println("주소:" + s.getAddr());
		ss.insertStudent(s);
		return "redirect:/getStdDbList";
	}

	@GetMapping("/showStudentDetail/{id}")
	public String showStudentDetail(@PathVariable("id") Long id, Model model) {
		Student s = ss.getStudentBySeq(id);
		System.out.println("ID:" + id);
		System.out.println("카톡ID:" + s.getKakao());
		model.addAttribute("stdDetail", s);
		return "student/showStdDetail";
	}

	@GetMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable("id") Long id, Model model) {
		ss.delStudentById(id);
		return "redirect:/getStdDbList";
	}

	@GetMapping("/editStdForm/{id}")
	public String editStudentForm(@PathVariable("id") Long id, Model model) {
		Student s = ss.getStudentBySeq(id);
		model.addAttribute("student", s);
		return "student/editStdForm";
	}

	@PostMapping("/updateStudent")
	public String updateStudent(@ModelAttribute Student s) {
		ss.updateStudent(s);
		return "redirect:/getStdDbList";
	}
}
