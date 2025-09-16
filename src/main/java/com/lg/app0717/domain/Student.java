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
public class Student {
	@Id @GeneratedValue
	private Long id;
	private String name;
	private String tel;
	private String addr;
	private String school;
	private String email;
	private String kakao;
}
