package com.example.uploadData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.example.entity.User;
import com.example.repository.Userrepo;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private Userrepo userRepository;

    @Override
    public void uploadEmployeeDetails(MultipartFile file) throws IOException {
        try (InputStream in = file.getInputStream()) {
            // Logic to read data from Excel file and create User objects
            List<User> users = readUsersFromExcel(in);

            // Encrypt default password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode("defaultPassword");

            for (User user : users) {
                // Check if empid already exists
                User existingUser = userRepository.findByEmpid(user.getEmpid());
                if (existingUser == null) {
                    // Set default password
                    user.setPassword(encodedPassword);
                    // Save user
                    userRepository.save(user);
                }
            }
        }
    }

private List<User> readUsersFromExcel(InputStream in) throws IOException {
    List<User> users = new ArrayList<>();

    // Creating a Workbook from the Excel file
    Workbook workbook = WorkbookFactory.create(in);

    // Assuming data is present in the first sheet (index 0)
    Sheet sheet = workbook.getSheetAt(0);

    // Iterate over rows in the sheet
    Iterator<Row> rowIterator = sheet.iterator();
    while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        
        // Skip header row
        if (row.getRowNum() == 0) {
            continue;
        }

        // Assuming data format: empid, name, empemail, password, empmobile, availability, privilage, designation
        // You may need to adjust this according to your actual Excel structure
        String empid = row.getCell(0).getStringCellValue();
        String name = row.getCell(1).getStringCellValue();
        String empemail = row.getCell(2).getStringCellValue();
        String password = row.getCell(3).getStringCellValue();
        String empmobile = row.getCell(4).getStringCellValue();
        boolean availability = row.getCell(5).getBooleanCellValue();
        String privilage = row.getCell(6).getStringCellValue();
        String designation = row.getCell(7).getStringCellValue();

        // Create a new User object and add it to the list
        User user = new User(empid, name, empemail, password, empmobile, availability, privilage, designation, null, null, null);
        users.add(user);
    }

    // Close the workbook to release resources
    workbook.close();

    return users;
}

}
