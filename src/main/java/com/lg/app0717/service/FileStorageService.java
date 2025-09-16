package com.lg.app0717.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(
        @Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir)
            .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("업로드 디렉토리 생성 실패", ex);
        }
    }

    /** 파일 저장 후, 서버에 저장된 파일명 반환 */
    public String storeFile(MultipartFile file) {
        String orig = file.getOriginalFilename();
        String ext = "";
        if (orig != null && orig.contains(".")) {
            ext = orig.substring(orig.lastIndexOf('.'));
        }
        String stored = System.currentTimeMillis() + ext;
        try {
            Path target = this.fileStorageLocation.resolve(stored);
            Files.copy(file.getInputStream(), target,
                       StandardCopyOption.REPLACE_EXISTING);
            return stored;
        } catch (IOException ex) {
            throw new RuntimeException("파일 저장 실패: " + orig, ex);
        }
    }

    /** 다운로드 시 리소스로 변환 */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation
                                .resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) return resource;
            else throw new RuntimeException(
                   "파일을 찾을 수 없습니다: " + fileName);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(
                "파일 URL 변환 오류: " + fileName, ex);
        }
    }
}