package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository("userRepository")
public interface Userrepo extends JpaRepository<User, String>
{

	User findByEmpemail (String email);
	User findByEmpid (String empid);
	Optional<User> findByName(String name);
	 List<User> findByPrivilage(String privilege);
	User findByToken(String token);
	
	
	@Query("SELECT u FROM User u WHERE u.name = ?1")
    User findByUsername(String name);
	
//	User saveOrUpdateProfilePicture(User user);
	
//    User saveOrUpdateProfilePicture(User user);


}
