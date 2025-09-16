package com.lg.app0717.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lg.app0717.domain.Student;
import com.lg.app0717.repository.StudentRepo;

@Service
public class StudentServiceImp implements StudentService {
	@Autowired
	StudentRepo sr;

	@Override
	public List<Student> getStdList() {
		List<Student> list = (List<Student>)sr.findAll();
		return list;
	}

	@Override
	public void insertStudent(Student s) {
		// TODO Auto-generated method stub
		sr.save(s);
	}

	@Override
	public Student getStudentBySeq(Long id) {
		// TODO Auto-generated method stub
		Student s = sr.findById(id).get();
		return s;
	}

	@Override
	public void delStudentById(Long id) {
		// TODO Auto-generated method stub
		sr.deleteById(id);
	}

	@Override
	public void updateStudent(Student s) {
		// TODO Auto-generated method stub
		sr.save(s);
	}
}
