package com.example.userprofile;


import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.entity.User;

public interface UserProfileService {
    User getUserByEmail(String email);
    User updateUserProfile(User user);
    User getUserByEmpid(String loggedInEmpId);
	void saveOrUpdateUser(User user);
	void saveUser(User user);
	void updateDesignation(String empid, String designation);
//    void saveOrUpdateProfilePicture(User user, MultipartFile file) throws IOException;

}
