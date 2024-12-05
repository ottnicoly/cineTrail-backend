package com.nicolyott.cineTrail.repository;

import com.nicolyott.cineTrail.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

}
