package com.threecoffee.springsec.repository;

import com.threecoffee.springsec.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}