package com.example.entity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.repository.Userrepo;

@Component
public class DatabaseInitializer {

    @Autowired
    private Userrepo userRepository; // Inject your repository here

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inject password encoder bean

    @PostConstruct
    public void initializeDatabase() {
        if (userRepository.count() == 0) { // Check if User table is empty
            User superAdmin = new User();
            superAdmin.setEmpid("1000");
            superAdmin.setName("Super Admin");
            superAdmin.setEmpemail("admin@example.com");
            superAdmin.setPassword(encryptPassword("Admin@123")); // Encrypt the password before saving
            superAdmin.setEmpmobile("1234567890");
            superAdmin.setAvailability(true);
            superAdmin.setPrivilage("SADMIN");
            superAdmin.setDesignation("Administrator");
            superAdmin.setEnable(true);

            userRepository.save(superAdmin);
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
