package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.VipPeriodDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.models.VipPeriod;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.repositories.UserVIPRepository;
import com.project.MovieWebsite.repositories.VipPeriodRepository;
import com.project.MovieWebsite.services.VipPeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VipPeriodServiceImpl implements VipPeriodService {

    private final UserRepository userRepository;
    private final UserVIPRepository userVIPRepository;
    private final VipPeriodRepository vipPeriodRepository;

    @Override
    public VipPeriod createVipPeriod(int userId) throws DataNotFoundException{

        if(vipPeriodRepository.findByUserId(userId)==null) {


            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + userId));


            VipPeriod newVipPeriod = VipPeriod.builder()
                    .user(existingUser)
                    .registrationDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now().plusDays(existingUser.getUserVip().getNumberMonth() * 30L))
                    .build();
            return vipPeriodRepository.save(newVipPeriod);
        }
        return updateVipPeriod(userId);
    }

    @Override
    public VipPeriod updateVipPeriod(int userId) throws DataNotFoundException{

        VipPeriod existingVipPeriod= vipPeriodRepository.findByUserId(userId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + userId));

        LocalDateTime now = LocalDateTime.now();

        existingVipPeriod.setUser(existingUser);
        existingVipPeriod.setRegistrationDate(now);

        if (now.isBefore(existingVipPeriod.getExpirationDate())) {
            long remainingDays = java.time.Duration.between(now, existingVipPeriod.getExpirationDate()).toDays();
            existingVipPeriod.setExpirationDate(now.plusDays(remainingDays + existingUser.getUserVip().getNumberMonth() * 30L));
        } else {
            existingVipPeriod.setExpirationDate(now.plusDays(existingUser.getUserVip().getNumberMonth() * 30L));
        }

        return vipPeriodRepository.save(existingVipPeriod);

    }

    @Override
    public void deleteVipPeriod(int userId) {
        VipPeriod vipPeriod= vipPeriodRepository.findByUserId(userId);
        vipPeriodRepository.deleteById(vipPeriod.getId());
    }
}