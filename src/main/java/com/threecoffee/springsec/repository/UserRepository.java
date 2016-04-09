package com.threecoffee.springsec.repository;

import com.threecoffee.springsec.domain.User;

public interface UserRepository {
    Iterable<User> findAll();

    User save(User user);

    User findUser(Long id);

    void deleteUser(Long id);
}