package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.UserVIP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVIPRepository extends JpaRepository<UserVIP, Integer> {
    UserVIP findByName(String name);
}
