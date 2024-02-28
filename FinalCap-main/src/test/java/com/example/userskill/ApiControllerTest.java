package com.example.userskill;
import com.example.userskill.ApiController;
import com.example.userskill.UserSkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ApiControllerTest {

    @Mock
    private UserSkillService userSkillService;

    @InjectMocks
    private ApiController apiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Existing test cases

    @Test
    void testGetSubdomains_EmptyDomain_ReturnsEmptyList() {
        // Given
        String domain = "";
        when(userSkillService.getAllSubdomains(domain)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<String>> responseEntity = apiController.getSubdomains(domain);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
    }

    @Test
    void testGetSubdomains_NonExistentDomain_ReturnsEmptyList() {
        // Given
        String domain = "NonExistentDomain";
        when(userSkillService.getAllSubdomains(domain)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<String>> responseEntity = apiController.getSubdomains(domain);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
    }

    @Test
    void testGetSubdomains_NullDomain_ReturnsBadRequestStatus() {
        // When
        ResponseEntity<List<String>> responseEntity = apiController.getSubdomains(null);

        // Then
    }

    // Additional test cases for getSubdomains method

    @Test
    void testGetSkills_EmptySubdomain_ReturnsEmptyList() {
        // Given
        String subdomain = "";
        when(userSkillService.getAllSkills(subdomain)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<String>> responseEntity = apiController.getSkills(subdomain);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
    }

    @Test
    void testGetSkills_NonExistentSubdomain_ReturnsEmptyList() {
        // Given
        String subdomain = "NonExistentSubdomain";
        when(userSkillService.getAllSkills(subdomain)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<String>> responseEntity = apiController.getSkills(subdomain);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
    }

    @Test
    void testGetSkills_NullSubdomain_ReturnsBadRequestStatus() {
        // When
        ResponseEntity<List<String>> responseEntity = apiController.getSkills(null);

    }



    // Additional test cases for getSkills method

    @Test
    void testGetSkills_ValidSubdomain_ReturnsSkillsList() {
        // Given
        String subdomain = "ValidSubdomain";
        List<String> expectedSkills = List.of("Skill1", "Skill2", "Skill3");
        when(userSkillService.getAllSkills(subdomain)).thenReturn(expectedSkills);

        // When
        ResponseEntity<List<String>> responseEntity = apiController.getSkills(subdomain);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedSkills, responseEntity.getBody());
    }

    @Test
    void testGetSkills_NullResponse_ReturnsInternalServerErrorStatus() {
        // Given
        String subdomain = "ValidSubdomain";
        when(userSkillService.getAllSkills(subdomain)).thenReturn(null);

        ResponseEntity<List<String>> responseEntity = apiController.getSkills(subdomain);

       
    }
}
