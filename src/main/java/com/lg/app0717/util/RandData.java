package com.lg.app0717.util;

public class RandData {	
	private static String[] name = { 
		"홍길동", "이길동", "박길동", "최길동", "김길동" };
	
	private static String[] tel = { 
		"010-1111-1111", "010-2222-2222", "010-3333-3333", 
		"010-4444-4444", "010-5555-5555" };

	private static String[] addr = {
		"대구 동구 신천1동", "인천 동구 신천1동", "광주 동구 신천1동", 
		"서울 동구 신천1동", "부산 동구 신천1동" };

	public static String getName() {
		return name[(int) (Math.random() * 5)];
	}

	public static String getTel() {
		return tel[(int) (Math.random() * 5)];
	}

	public static String getAddr() {
		return addr[(int) (Math.random() * 5)];
	}
}
