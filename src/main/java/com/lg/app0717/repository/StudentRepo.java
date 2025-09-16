package com.lg.app0717.repository;

import org.springframework.data.repository.CrudRepository;
import com.lg.app0717.domain.Student;

public interface StudentRepo extends CrudRepository<Student, Long>{

}
