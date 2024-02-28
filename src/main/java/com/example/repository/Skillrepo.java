package com.example.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Skills;

import jakarta.transaction.Transactional;

public interface Skillrepo extends JpaRepository<Skills, Integer> {

    Optional<Skills> findBySkillname(String skillname);

    List<Skills> findByDomain(String domain);

    List<Skills> findByDomainAndSubdomain(String domain, String subdomain);

    @Query("SELECT DISTINCT s.domain FROM Skills s")
    List<String> findDistinctDomains();

    @Query("SELECT DISTINCT s.subdomain FROM Skills s WHERE s.domain = :domain")
    List<String> findSubdomainsByDomain(String domain);

    @Query("SELECT s.skillname FROM Skills s WHERE s.subdomain = ?1")
    List<String> findSkillNamesBySubdomain(String subdomain);

    Optional<Skills> findBySkillnameAndDomainAndSubdomain(String skillname, String domain, String subdomain);

    List<Skills> findByDomainAndSubdomainAndSkillname(String domain, String subdomain, String skillname);

    // Custom query method to find skills by domain and subdomain (case-insensitive)
    List<Skills> findByDomainAndSubdomainIgnoreCase(String domain, String subdomain);

    boolean existsByDomainIgnoreCase(String domain);

    List<Skills> findBySubdomain(String selectedSubdomain);

    @Modifying
    @Query("UPDATE Skills s SET s.domain = :updatedDomain WHERE lower(s.domain) = lower(:existingDomain)")
    void updateDomain(@Param("existingDomain") String existingDomain, @Param("updatedDomain") String updatedDomain);

    @Modifying
    @Query("UPDATE Skills s SET s.subdomain = :updatedSubdomain WHERE lower(s.domain) = lower(:domain) AND lower(s.subdomain) = lower(:existingSubdomain)")
    void updateSubdomain(@Param("domain") String domain,
                         @Param("existingSubdomain") String existingSubdomain,
                         @Param("updatedSubdomain") String updatedSubdomain);

    @Query("SELECT s.skillname FROM Skills s WHERE s.domain = :domain AND s.subdomain = :subdomain")
    List<String> findOldSkills(@Param("domain") String domain, @Param("subdomain") String subdomain);

    @Transactional
    @Modifying
    @Query("UPDATE Skills s SET s.skillname = :newSkillName WHERE s.domain = :domain AND s.subdomain = :subdomain AND s.skillname = :oldSkillName")
    void updateSkillName(@Param("domain") String domain,
                         @Param("subdomain") String subdomain,
                         @Param("oldSkillName") String oldSkillName,
                         @Param("newSkillName") String newSkillName);

    // New method to find skill by ID
    Optional<Skills> findById(Integer skillId);
}
