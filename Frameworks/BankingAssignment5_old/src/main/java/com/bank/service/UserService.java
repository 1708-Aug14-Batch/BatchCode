package com.bank.service;

import com.bank.dto.UserDto;

public interface UserService {
	
	public UserDto authenticateUser(UserDto userDto);
	
	public boolean createUser(UserDto userDto);

}