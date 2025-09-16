package com.lg.app0717.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

// 임시 테스트용 컨트롤러 - 문제 해결 후 제거하세요
@Controller
public class TestController {
    
    @GetMapping("/test-username")
    @ResponseBody
    public Map<String, Object> testUsername(@RequestParam String username) {
        Map<String, Object> response = new HashMap<>();
        
        System.out.println("테스트 요청 받음: " + username);
        
        response.put("available", true);
        response.put("message", "테스트 성공: " + username);
        response.put("debug", "컨트롤러가 정상 작동합니다");
        
        return response;
    }
    
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "컨트롤러 테스트 성공!";
    }
}