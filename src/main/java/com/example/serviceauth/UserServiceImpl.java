package com.example.serviceauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.repository.Userrepo;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private Userrepo userRepository;

	@Override
	public User save(UserDto userDto) {
		userDto.setPrivilage("USER");
		User user = new User(userDto.getEmpid(),userDto.getName(),userDto.getEmpemail(), passwordEncoder.encode(userDto.getPassword()) ,userDto.getEmpmobile(), userDto.getAvailability(),userDto.getPrivilage(), null, null, null, null);
		return userRepository.save(user);
	}

	
}
