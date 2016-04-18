package com.threecoffee.springsec;

import com.threecoffee.springsec.config.SecurityConfiguration;
import com.threecoffee.springsec.domain.User;
import com.threecoffee.springsec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan
@EnableJpaRepositories
public class SpringSecApp {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public Converter<String, User> messageConverter() {
        return new Converter<String, User>() {
            @Override
            public User convert(String id) {
                return userRepository.findOne(Long.valueOf(id));
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(new Class[] {SecurityConfiguration.class, SpringSecApp.class}, args);
    }
}
