package com.lg.app0717.controller;

import com.lg.app0717.domain.User;
import com.lg.app0717.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        
        if (error != null) {
            model.addAttribute("errorMessage", "사용자명 또는 비밀번호가 올바르지 않습니다.");
        }
        
        if (logout != null) {
            model.addAttribute("logoutMessage", "성공적으로 로그아웃되었습니다.");
        }
        
        return "auth/login";
    }
    
    // 회원가입 페이지
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }
    
    // 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            // 입력 유효성 검사
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "사용자명을 입력해주세요.");
                return "redirect:/register";
            }
            
            if (user.getPassword() == null || user.getPassword().length() < 4) {
                redirectAttributes.addFlashAttribute("errorMessage", "비밀번호는 4자 이상이어야 합니다.");
                return "redirect:/register";
            }
            
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "이메일을 입력해주세요.");
                return "redirect:/register";
            }
            
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "이름을 입력해주세요.");
                return "redirect:/register";
            }
            
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다. 로그인해주세요.");
            return "redirect:/login";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }
    
    // 사용자명 중복 체크 (AJAX용) - 수정된 부분
    @GetMapping("/check-username")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkUsername(@RequestParam("username") String username) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (username == null || username.trim().isEmpty()) {
                response.put("available", false);
                response.put("message", "사용자명을 입력해주세요.");
                return ResponseEntity.ok(response);
            }
            
            if (username.trim().length() < 3) {
                response.put("available", false);
                response.put("message", "사용자명은 3자 이상이어야 합니다.");
                return ResponseEntity.ok(response);
            }
            
            boolean isAvailable = !userService.isUsernameExists(username.trim());
            response.put("available", isAvailable);
            response.put("message", isAvailable ? "사용 가능한 사용자명입니다." : "이미 사용중인 사용자명입니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("available", false);
            response.put("message", "사용자명 확인 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
    
    // 이메일 중복 체크 (AJAX용) - 수정된 부분
    @GetMapping("/check-email")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam("email") String email) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (email == null || email.trim().isEmpty()) {
                response.put("available", false);
                response.put("message", "이메일을 입력해주세요.");
                return ResponseEntity.ok(response);
            }
            
            // 이메일 형식 검사
            if (!email.matches("^[^\s@]+@[^\s@]+\\.[^\s@]+$")) {
                response.put("available", false);
                response.put("message", "올바른 이메일 형식을 입력해주세요.");
                return ResponseEntity.ok(response);
            }
            
            boolean isAvailable = !userService.isEmailExists(email.trim());
            response.put("available", isAvailable);
            response.put("message", isAvailable ? "사용 가능한 이메일입니다." : "이미 사용중인 이메일입니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("available", false);
            response.put("message", "이메일 확인 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
    
    // 마이페이지
    @GetMapping("/mypage")
    public String myPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            User user = userService.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
            model.addAttribute("user", user);
            return "auth/mypage";
        }
        
        return "redirect:/login";
    }
    
    // 사용자 정보 수정
    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute User userDetails, 
                               RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if (auth != null && auth.isAuthenticated()) {
                User currentUser = userService.findByUsername(auth.getName())
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
                
                userService.updateUser(currentUser.getId(), userDetails);
                redirectAttributes.addFlashAttribute("successMessage", "프로필이 성공적으로 수정되었습니다.");
            }
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/mypage";
    }
}