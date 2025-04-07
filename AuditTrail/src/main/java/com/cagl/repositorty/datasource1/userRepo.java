package com.cagl.repositorty.datasource1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cagl.entity.datasource1.User;

@Repository
public interface userRepo extends JpaRepository<User, String> {

}
