package com.lg.app0717.repository;

import org.springframework.data.repository.CrudRepository;
import com.lg.app0717.domain.Person;

public interface BoardRepository extends CrudRepository<Person, Long>{

}
