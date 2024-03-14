package com.example.userprofile;


import com.example.entity.User;
import com.example.repository.Userrepo;

import jakarta.transaction.Transactional;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final Userrepo userRepository;

    @Autowired
    public UserProfileServiceImpl(Userrepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmpid(String empid) {
        // Retrieve a user by empid (username) from the UserRepository
        return userRepository.findByEmpid(empid);
    }

    @Override
    public User getUserByEmail(String email) {
        // Retrieve a user by email from the UserRepository
        return userRepository.findByEmpemail(email);
    }

    @Override
    public User updateUserProfile(User user) {
        // Update the user's profile in the UserRepository
        return userRepository.save(user);
    }
    
    public void saveOrUpdateUser(User user) {
        if (user.getEmpid() == null) {
            // If user doesn't have an empid, it means it's a new user, so we save it
            userRepository.save(user);
        } else {
            // If user has an empid, it means it's an existing user, so we update it
            User existingUser = userRepository.findByEmpid(user.getEmpid());
            if (existingUser != null) {
                // Update existing user properties
                existingUser.setName(user.getName());
                existingUser.setEmpemail(user.getEmpemail());
                existingUser.setPassword(user.getPassword());
                existingUser.setEmpmobile(user.getEmpmobile());
                existingUser.setAvailability(user.getAvailability());
                existingUser.setPrivilage(user.getPrivilage());
                // Save the updated user
                userRepository.save(existingUser);
            } else {
                // Handle the case when user with the provided empid doesn't exist
                // You might throw an exception or handle it according to your application's requirements
            }
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
    
    

    



    // Implement method to get currently logged in user
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String empid = userDetails.getUsername(); // Assuming the username is the empid
            return userRepository.findByEmpid(empid);
        } else {
            // Handle case when user is not authenticated or UserDetails is not available
            return null;
        }
    
}


    
    @Override
    public void updateDesignation(String empid, String designation) {
        User user = userRepository.findByEmpid(empid);
        if (user != null) {
            user.setDesignation(designation);
            userRepository.save(user);
        } else {
            // Handle user not found
        }
    }

}


