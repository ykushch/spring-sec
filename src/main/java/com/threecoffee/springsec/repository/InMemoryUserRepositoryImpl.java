package com.threecoffee.springsec.repository;

import com.threecoffee.springsec.domain.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final AtomicLong COUNTER = new AtomicLong();
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public Iterable<User> findAll() {
        return this.users.values();
    }

    @Override
    public User save(User user) {
        Long id = user.getId();

        if (id == null) {
            id = COUNTER.incrementAndGet();
            user.setId(id);
        }
        this.users.put(id, user);

        return user;
    }

    @Override
    public User findUser(Long id) {
        return this.users.get(id);
    }

    @Override
    public void deleteUser(Long id) {
        this.users.remove(id);
    }
}
