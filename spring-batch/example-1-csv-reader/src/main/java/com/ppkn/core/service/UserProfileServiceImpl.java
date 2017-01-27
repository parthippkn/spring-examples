package com.ppkn.core.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppkn.core.domain.UserProfile;
import com.ppkn.core.dto.UserDto;
import com.ppkn.core.repo.UserProfileRepository;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	private static Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	@Autowired
	private UserProfileRepository userProfileRepository;

	public void saveUsers(List<? extends UserDto> users) {

		if (!users.isEmpty()) {

			logger.debug("Persist user size : " + users.size());
			List<UserProfile> domainList = new ArrayList<UserProfile>();
			users.forEach(userdto -> {
				domainList.add(convertDtoToDomain(userdto));
			});
			userProfileRepository.save(domainList);
		}
	}

	private UserProfile convertDtoToDomain(UserDto userDto) {

		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName(userDto.getFirstName());
		userProfile.setLastName(userDto.getLastName());
		userProfile.setEmail(userDto.getEmail());
		return userProfile;
	}

}
