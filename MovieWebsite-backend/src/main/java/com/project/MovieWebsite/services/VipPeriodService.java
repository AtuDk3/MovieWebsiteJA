package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.VipPeriodDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.VipPeriod;

public interface VipPeriodService {

    VipPeriod createVipPeriod(int userId) throws DataNotFoundException;

    VipPeriod updateVipPeriod(int userId) throws DataNotFoundException;

    void deleteVipPeriod(int userId);
}