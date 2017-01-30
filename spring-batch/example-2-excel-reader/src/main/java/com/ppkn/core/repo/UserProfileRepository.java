package com.ppkn.core.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ppkn.core.domain.UserProfile;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

}
