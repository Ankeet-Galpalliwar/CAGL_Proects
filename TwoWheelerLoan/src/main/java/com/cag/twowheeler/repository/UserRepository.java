package com.cag.twowheeler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cag.twowheeler.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByUserID(String userID);

}
