//package com.example.logincontroller.repository;
//import com.example.entity.Skills;
//import com.example.repository.Skillrepo;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//public class SkillrepoTest {
//
//    @Autowired
//    private Skillrepo skillrepo;
//
//    @Test
//    public void testFindBySkillname() {
//        // Given
//        String skillname = "Java";
//
//        // When
//        Optional<Skills> skillsOptional = skillrepo.findBySkillname(skillname);
//
//        // Then
//        assertTrue(skillsOptional.isPresent());
//        assertEquals(skillname, skillsOptional.get().getSkillname());
//    }
//
//    // Write similar test methods for other repository methods...
//
//    // Example test for a custom query method
//    @Test
//    public void testFindDistinctDomains() {
//        // When
//        List<String> distinctDomains = skillrepo.findDistinctDomains();
//
//        // Then
//        assertNotNull(distinctDomains);
//        assertFalse(distinctDomains.isEmpty());
//        // Add more assertions as needed
//    }
//}
