package com.example.uploadData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class UploadController {

    @Autowired
    private UploadService UploadService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
        	UploadService.uploadEmployeeDetails(file);
            redirectAttributes.addFlashAttribute("message", "File uploaded successfully");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload file: " + e.getMessage());
        }
        return "redirect:/";
    }
    
    @GetMapping("/showUploadForm")
    public ModelAndView showUploadForm() {
        ModelAndView modelAndView = new ModelAndView("upload");
        return modelAndView;
    }
}
