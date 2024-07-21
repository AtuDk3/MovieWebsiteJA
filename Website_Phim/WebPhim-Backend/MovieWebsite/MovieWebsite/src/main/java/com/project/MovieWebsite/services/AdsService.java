
package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.AdsDTO;
import com.project.MovieWebsite.models.Ads;
import com.project.MovieWebsite.responses.AdsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AdsService {
    Ads createAds(AdsDTO adsDTO);

    Ads createOrderAds(int adsId, AdsDTO adsDTO) throws Exception;

    Ads getAdsById(int adsId);

    Ads checkTradingCode(String tradingCode) throws Exception;

    Page<AdsResponse> getAllAds(String keyword, PageRequest pageRequest);

    Page<AdsResponse> getAllAdsAdmin(PageRequest pageRequest);

    Ads updateAdsPayment(String trading_code) throws Exception;

    void updateAds(int adsId, AdsDTO adsDTO) throws Exception;

    AdsResponse mapToAdsResponse(Ads ads);

    void deleteAds(int adsId);
}
