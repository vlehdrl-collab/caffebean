package com.lg.app0717.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
// DTO(Data Transfer Object)
public class PersonTest {
	@Id @GeneratedValue
	private Long seq;
	private String name;
	private String tel;
	private String addr;
}
