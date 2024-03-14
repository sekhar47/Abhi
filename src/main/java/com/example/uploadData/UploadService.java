package com.example.uploadData;

import com.example.entity.User;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface UploadService {
    void uploadEmployeeDetails(MultipartFile file) throws IOException;
}
