
package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.AdsDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Ads;
import com.project.MovieWebsite.models.AdsImage;
import com.project.MovieWebsite.repositories.AdsImageRepository;
import com.project.MovieWebsite.repositories.AdsRepository;
import com.project.MovieWebsite.responses.AdsResponse;
import com.project.MovieWebsite.services.AdsService;
import com.project.MovieWebsite.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor

public class AdsServiceImpl implements AdsService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private final AdsRepository adsRepository;
    private final AdsImageRepository adsImageRepository;
    private final ClientService clientService;

    @Override
    public Ads createAds(AdsDTO adsDTO) {

        Ads newAds = Ads.builder()
                .email(adsDTO.getEmail())
                .name(adsDTO.getName())
                .description(adsDTO.getDescription())
                .position(adsDTO.getPosition())
                .video(adsDTO.getVideo())
                .numberDays(adsDTO.getNumberDays())
                .amount(adsDTO.getAmount())
                .isActive(adsDTO.getIsActive())
                .isConfirm(adsDTO.getIsConfirm())
                .build();
        Ads adsExisting = adsRepository.save(newAds);
        for(String image: adsDTO.getListImg()){
            AdsImage newAdsImgae = AdsImage.builder()
                    .ads(adsExisting)
                    .imageUrl(image)
                    .build();
            adsImageRepository.save(newAdsImgae);
        }
        return adsRepository.save(newAds);
    }

    @Override
    public Ads createOrderAds(int adsId, AdsDTO adsDTO) throws Exception{
        Ads ads= adsRepository.findById(adsId).orElseThrow(() -> new RuntimeException("Ads not found!"));
        String trading_code="";
        while (true){
            trading_code= generateRandomTradingCode(10);
            if(adsRepository.findByTradingCode(trading_code)!=null){
                continue;
            }
            break;
        }
        ads.setEmail(adsDTO.getEmail());
        ads.setTradingCode(trading_code);
        ads.setNumberDays(adsDTO.getNumberDays());
        ads.setAmount(adsDTO.getAmount());
        return adsRepository.save(ads);
    }


    public String generateRandomTradingCode(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return stringBuilder.toString();
    }

    @Override
    public Ads getAdsById(int adsId) {
        return adsRepository.findById(adsId).orElseThrow(() -> new RuntimeException("Ads not found!"));
    }

    @Override
    public Ads checkTradingCode(String tradingCode) throws Exception{
        Ads ads= adsRepository.findByTradingCode(tradingCode);
        if(ads!=null && ads.getIsConfirm()!=1){
            return ads;
        }else{
            throw new DataNotFoundException("Error Trading Code");
        }
    }

    @Override
    public Page<AdsResponse> getAllAds(String keyword, PageRequest pageRequest) {
        Page<Ads> adsPage = adsRepository.getAllAds(pageRequest);
        return mapToAdsResponsePage(adsPage);
    }

    @Override
    public Page<AdsResponse> getAllAdsAdmin(PageRequest pageRequest) {
        Page<Ads> adsPage = adsRepository.getAllAdsAdmin(pageRequest);
        return mapToAdsResponsePageAdmin(adsPage);
    }

    @Override
    public Ads updateAdsPayment(String trading_code) throws Exception{
        Ads ads= adsRepository.findByTradingCode(trading_code);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (ads!=null){
            ads.setIsConfirm(1);
            ads.setCreateAt(now);
            ads.setExpirationAt(now.plusDays(ads.getNumberDays()*1L));
            clientService.sendAdsSuccess(ads.getCreateAt().format(formatter),
                                            ads.getExpirationAt().format(formatter),
                                            ads.getEmail());
            return adsRepository.save(ads);
        }else{
            throw new DataNotFoundException("Error Existing Ads");
        }
    }

    @Override
    public void updateAds(int adsId, AdsDTO adsDTO) throws Exception {
        Ads ads= adsRepository.findById(adsId).orElseThrow(() -> new RuntimeException("Ads not found!"));
        List<AdsImage> adsImages= adsImageRepository.findByAds(ads);
        for(AdsImage adsImage: adsImages){
            adsImageRepository.delete(adsImage);
        }
        ads.setName(adsDTO.getName());
        ads.setDescription(adsDTO.getDescription());
        ads.setIsActive(adsDTO.getIsActive());
        for(String image: adsDTO.getListImg()){
            AdsImage newAdsImgae = AdsImage.builder()
                    .ads(ads)
                    .imageUrl(image)
                    .build();
            adsImageRepository.save(newAdsImgae);
        }
    }

    @Override
    public AdsResponse mapToAdsResponse(Ads ads) {
        List<AdsImage> listImg=  adsImageRepository.findByAds(ads);
        List<String> lists= new ArrayList<>();
        for(AdsImage adsImage: listImg){
            lists.add(adsImage.getImageUrl());
        }
        AdsResponse adsResponse = AdsResponse.builder()
                .id(ads.getId())
                .name(ads.getName())
                .description(ads.getDescription())
                .position(ads.getPosition())
                .video(ads.getVideo())
                .tradingCode(ads.getTradingCode())
                .email(ads.getEmail())
                .createAt(convertToDate(ads.getCreateAt()))
                .expirationAt(convertToDate(ads.getExpirationAt()))
                .amount(ads.getAmount())
                .isActive(ads.getIsActive())
                .isConfirm(ads.getIsConfirm())
                .listImg(lists)
                .build();
        return adsResponse;
    }

    @Override
    public void deleteAds(int adsId) {
        adsRepository.deleteById(adsId);
    }

    private Page<AdsResponse> mapToAdsResponsePageAdmin(Page<Ads> adsPage) {
        return adsPage.map(ads -> {

                List<AdsImage> listImg = adsImageRepository.findByAds(ads);
                List<String> lists = new ArrayList<>();
                for (AdsImage adsImage : listImg) {
                    lists.add(adsImage.getImageUrl());
                }
                AdsResponse adsResponse = AdsResponse.builder()
                        .id(ads.getId())
                        .name(ads.getName())
                        .description(ads.getDescription())
                        .position(ads.getPosition())
                        .video(ads.getVideo())
                        .tradingCode(ads.getTradingCode())
                        .email(ads.getEmail())
                        .createAt(convertToDate(ads.getCreateAt()))
                        .expirationAt(convertToDate(ads.getExpirationAt()))
                        .amount(ads.getAmount())
                        .isActive(ads.getIsActive())
                        .isConfirm(ads.getIsConfirm())
                        .listImg(lists)
                        .build();
                return adsResponse;
        });
    }

    private Page<AdsResponse> mapToAdsResponsePage(Page<Ads> adsPage) {
        return adsPage.map(ads -> {
            LocalDateTime now= LocalDateTime.now();
            if(now.isAfter(ads.getExpirationAt()) && ads.getIsConfirm()==1) {
                ads.setIsConfirm(0);
                adsRepository.save(ads);
                clientService.sendAdsExpiration(ads.getEmail());
                return null;
            }else {
                List<AdsImage> listImg = adsImageRepository.findByAds(ads);
                List<String> lists = new ArrayList<>();
                for (AdsImage adsImage : listImg) {
                    lists.add(adsImage.getImageUrl());
                }
                AdsResponse adsResponse = AdsResponse.builder()
                        .id(ads.getId())
                        .name(ads.getName())
                        .description(ads.getDescription())
                        .position(ads.getPosition())
                        .video(ads.getVideo())
                        .tradingCode(ads.getTradingCode())
                        .email(ads.getEmail())
                        .createAt(convertToDate(ads.getCreateAt()))
                        .expirationAt(convertToDate(ads.getExpirationAt()))
                        .amount(ads.getAmount())
                        .isActive(ads.getIsActive())
                        .isConfirm(ads.getIsConfirm())
                        .listImg(lists)
                        .build();
                return adsResponse;
            }
        });


    }
    private static Date convertToDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
