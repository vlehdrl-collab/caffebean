package com.lg.app0717.service;

import com.lg.app0717.domain.User;
import com.lg.app0717.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // 회원가입
    public User registerUser(User user) {
        // 중복 체크
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
    }
    
    // 사용자명으로 사용자 찾기
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // 이메일로 사용자 찾기
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // 사용자명 중복 체크
    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
    // 이메일 중복 체크
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // ID로 사용자 찾기
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    // 사용자 정보 수정
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        
        // 비밀번호가 변경된 경우에만 암호화
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        
        return userRepository.save(user);
    }
    
    // 사용자 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}