package com.cagl.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cagl.entity.User;

@Repository
public interface userRepo extends JpaRepository<User, String> {

}
