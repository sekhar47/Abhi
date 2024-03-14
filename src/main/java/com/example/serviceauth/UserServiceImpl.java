package com.example.serviceauth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.repository.Userrepo;

import jakarta.transaction.Transactional;


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

	@Override
	public void increaseFailedAttempt(User user) {
		// TODO Auto-generated method stub
		System.out.println("hiiii increased ");
		int attempt = user.getFailedAttempt()+1;
		System.out.println("attempt increased " + attempt);
		System.out.println("employee id - " + user.getEmpid());
		userRepository.updateFailedAttempt(attempt, user.getEmpid());
		
	}
	
	private static final long lock_duration_time=1*60*1000;
	
	public static final long ATTEMPT_TIME = 3;
	@Override
	public void resetAttempt(String empemail) {
		// TODO Auto-generated method stub
		userRepository.updateFailedAttempt(0, empemail);
		
	}

	@Override
	public void lock(User user) {
		// TODO Auto-generated method stub
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		userRepository.save(user);
	}

	@Override
	public boolean unlockAccountTimeExpired(User user) {
		// TODO Auto-generated method stub
		long lockTimeInMills = user.getLockTime().getTime();
		long currentTimeMilllis = System.currentTimeMillis();

		if(lockTimeInMills+lock_duration_time < currentTimeMilllis) {
			
			user.setAccountNonLocked(true);
			user.setLockTime(null);
			user.setFailedAttempt(0);
			System.out.println("klasdkalsdaldklasdasd.....");
			userRepository.save(user);
			System.out.println("dggfgdgdg...");
			return true;
		}
		return false;
	}
	
	
	
}
