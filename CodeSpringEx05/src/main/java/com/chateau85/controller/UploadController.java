package com.chateau85.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UploadController {
    private static final long MAX_UPLOAD_BYTES = 10L * 1024L * 1024L;
    private static final Path UPLOAD_ROOT = Paths.get(
            System.getenv().getOrDefault("UPLOAD_DIR", "C:\\upload")).toAbsolutePath().normalize();

    @GetMapping("/uploadForm")
    public void uploadForm() {
        log.info("upload form");
    }

    @PostMapping("/uploadFormAction")
    public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
        storeAll(uploadFile);
    }

    @GetMapping("/uploadAjax")
    public void uploadAjax() {
        log.info("upload ajax");
    }

    @PostMapping("/uploadAjaxAction")
    public void uploadAjaxPost(MultipartFile[] uploadFile) {
        storeAll(uploadFile);
    }

    private void storeAll(MultipartFile[] files) {
        if (files == null) {
            return;
        }
        for (MultipartFile file : files) {
            try {
                store(file);
            } catch (IOException | IllegalArgumentException e) {
                log.warn("Upload rejected: {}", sanitizeForLog(e.getMessage()));
            }
        }
    }

    private static String sanitizeForLog(String value) {
        return value == null ? "unknown" : value.replace((char) 13, '_').replace((char) 10, '_');
    }

    private void store(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }
        if (file.getSize() > MAX_UPLOAD_BYTES) {
            throw new IllegalArgumentException("File exceeds 10 MiB limit");
        }

        String originalName = file.getOriginalFilename();
        String normalizedName = originalName == null ? "upload.bin" : originalName.replace('\\', '/');
        int lastSeparator = normalizedName.lastIndexOf('/');
        String safeName = normalizedName.substring(lastSeparator + 1);
        safeName = safeName.replaceAll("[^A-Za-z0-9._-]", "_");
        if (safeName.isEmpty() || ".".equals(safeName) || "..".equals(safeName)) {
            throw new IllegalArgumentException("Invalid file name");
        }

        Files.createDirectories(UPLOAD_ROOT);
        Path destination = UPLOAD_ROOT.resolve(UUID.randomUUID() + "_" + safeName).normalize();
        if (!destination.startsWith(UPLOAD_ROOT)) {
            throw new IllegalArgumentException("Invalid upload path");
        }
        try (java.io.InputStream input = file.getInputStream()) {
            Files.copy(input, destination);
        }
        log.info("Stored upload as {} ({} bytes)", destination.getFileName(), file.getSize());
    }
}