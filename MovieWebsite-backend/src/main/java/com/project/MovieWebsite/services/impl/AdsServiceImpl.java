package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.AdsDTO;
import com.project.MovieWebsite.models.Ads;
import com.project.MovieWebsite.repositories.AdsRepository;
import com.project.MovieWebsite.responses.AdsResponse;
import com.project.MovieWebsite.services.AdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;

    @Override
    public Ads createAds(AdsDTO adsDTO) {
        Ads newAds = Ads.builder().name(adsDTO.getName())
                .description(adsDTO.getDescription())
                .bannerAds(adsDTO.getBannerAds())
                .createAt(adsDTO.getCreateAt())
                .expirationAt(adsDTO.getExpirationAt())
                .amount(adsDTO.getAmount()).
                isActive(adsDTO.getIsActive()).build();
        return adsRepository.save(newAds);
    }

    @Override
    public Ads getAdsById(int adsId) {
        return adsRepository.findById(adsId).orElseThrow(() -> new RuntimeException("Ads not found!"));
    }

    @Override
    public Page<AdsResponse> getAllAds(String keyword, PageRequest pageRequest) {
        Page<Ads> adsPage = adsRepository.getAllAds(pageRequest);
        return mapToAdsResponsePage(adsPage);
    }

    @Override
    public Ads updateAds(int adsId, AdsDTO adsDTO) {
        Ads existsAds = getAdsById(adsId);
        existsAds.setName(adsDTO.getName());
        existsAds.setDescription(adsDTO.getDescription());
        existsAds.setBannerAds(adsDTO.getBannerAds());
        existsAds.setCreateAt(adsDTO.getCreateAt());
        existsAds.setExpirationAt(adsDTO.getExpirationAt());
        existsAds.setAmount(adsDTO.getAmount());
        existsAds.setIsActive(adsDTO.getIsActive());
        adsRepository.save(existsAds);
        return existsAds;
    }

    @Override
    public void deleteAds(int adsId) {
        adsRepository.deleteById(adsId);
    }

    private Page<AdsResponse> mapToAdsResponsePage(Page<Ads> adsPage) {
        return adsPage.map(ads -> {
            AdsResponse adsResponse = AdsResponse.builder()
                    .id(ads.getId())
                    .name(ads.getName())
                    .description(ads.getDescription())
                    .bannerAds(ads.getBannerAds())
                    .createAt(ads.getCreateAt())
                    .expirationAt(ads.getExpirationAt())
                    .amount(ads.getAmount())
                    .isActive(ads.getIsActive())
                    .build();
            return adsResponse;
        });
    }
}
