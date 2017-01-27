package com.ppkn.core.service;

import java.util.List;

import com.ppkn.core.dto.UserDto;

public interface UserProfileService {

	public void saveUsers(List<? extends UserDto> users);
}
