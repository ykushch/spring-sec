package com.threecoffee.springsec;

import com.threecoffee.springsec.domain.User;
import com.threecoffee.springsec.repository.InMemoryUserRepositoryImpl;
import com.threecoffee.springsec.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;

@SpringBootApplication
@ComponentScan
public class SpringSecApp {

    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepositoryImpl();
    }

    @Bean
    public Converter<String, User> messageConverter() {
        return new Converter<String, User>() {
            @Override
            public User convert(String id) {
                return userRepository().findUser(Long.valueOf(id));
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecApp.class, args);
    }
}
