package com.example.serviceauth;

import com.example.dto.UserDto;
import com.example.entity.User;

public interface UserService {
	
	User save(UserDto userDto);
	
	public void increaseFailedAttempt(User user);
	
	public void resetAttempt(String empemail);
	
	public void lock(User user);
	
	public boolean unlockAccountTimeExpired(User user);
	

}
