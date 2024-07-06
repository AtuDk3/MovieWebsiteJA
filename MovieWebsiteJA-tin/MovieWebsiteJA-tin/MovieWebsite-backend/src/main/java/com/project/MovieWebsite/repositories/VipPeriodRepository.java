package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.models.VipPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VipPeriodRepository extends JpaRepository<VipPeriod, Integer> {

    VipPeriod findByUserId (int userId);

}
