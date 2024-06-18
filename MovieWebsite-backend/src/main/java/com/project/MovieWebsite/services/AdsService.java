package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.AdsDTO;
import com.project.MovieWebsite.models.Ads;
import com.project.MovieWebsite.responses.AdsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AdsService {
    Ads createAds(AdsDTO adsDTO);

    Ads getAdsById(int adsId);

    Page<AdsResponse> getAllAds(String keyword, PageRequest pageRequest);

    Ads updateAds(int adsId, AdsDTO adsDTO);

    void deleteAds(int adsId);
}
