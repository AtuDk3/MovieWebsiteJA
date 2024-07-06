package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String token);
}
