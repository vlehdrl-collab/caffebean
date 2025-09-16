package com.lg.app0717.service;

import java.util.List;
import com.lg.app0717.domain.Student;

public interface StudentService {
	List<Student> getStdList();
	void insertStudent(Student s);
	Student getStudentBySeq(Long id);
	void delStudentById(Long id);
	void updateStudent(Student s);
}