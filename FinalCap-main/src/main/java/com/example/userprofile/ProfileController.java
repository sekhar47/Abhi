package com.example.userprofile;


import com.example.entity.User;
import com.example.repository.Userrepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import java.io.File;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserProfileService userService;
    
    @Autowired
    private Userrepo userRepository;

    @Autowired
    public ProfileController(UserProfileService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // Fetch logged-in user's profile and add it to the model
        String loggedInEmpId = principal.getName(); // Get the logged-in user's empid (username)
        User user = userService.getUserByEmpid(loggedInEmpId);

        if (user != null) {
            model.addAttribute("user", user);
        } else {
            // Handle the case when user is not found
        }

        return "profile";
    }




    @GetMapping("/editAvailability")
    public String editAvailability(Model model, Principal principal) {
        String loggedInEmpId = principal.getName();
        User user = userService.getUserByEmpid(loggedInEmpId);
        model.addAttribute("user", user);
        return "editAvailability";
    }

    // Method to handle update profile request
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute User updatedUser, Principal principal) {
        String loggedInEmpId = principal.getName();
        User currentUser = userService.getUserByEmpid(loggedInEmpId);
        
        // Update only necessary fields
       
        currentUser.setEmpmobile(updatedUser.getEmpmobile());
        currentUser.setAvailability(updatedUser.getAvailability());
        currentUser.setDesignation(updatedUser.getDesignation()); // Update designation
        userService.updateUserProfile(currentUser);
        return "redirect:/profile";
    }
    

//    @PostMapping("/updatePicture")
//    public ResponseEntity<String> updateProfilePicture(@RequestParam("file") MultipartFile file) {
//        // Logic to handle profile picture update
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("File is required", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // Normalize the file name
//            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//            // Save the file to the upload directory
//            String uploadDir = "path/to/your/upload/directory";
//            File uploadPath = new File(uploadDir);
//            if (!uploadPath.exists()) {
//                uploadPath.mkdirs();
//            }
//
//            try (FileOutputStream fos = new FileOutputStream(uploadDir + File.separator + fileName)) {
//                fos.write(file.getBytes());
//            }
//
//            return new ResponseEntity<>("Profile picture updated successfully", HttpStatus.OK);
//        } catch (IOException ex) {
//            return new ResponseEntity<>("Failed to update profile picture", HttpStatus.INTERNAL_SERVER_ERROR);
//        }}
//
//    private User getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String empid = userDetails.getUsername(); // Assuming the username is the empid
//            return userRepository.findByEmpid(empid); // Assuming you have access to userRepository
//        } else {
//            // Handle case when user is not authenticated or UserDetails is not available
//            return null;
//        }
//    }

}
