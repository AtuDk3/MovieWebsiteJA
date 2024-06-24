
package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Ads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
    boolean existsByName(String name);

    Optional<Ads> findById(int adsId);

    Ads findByTradingCode(String tradingCode);

    @Query("select a from Ads a " +
            "where a.isConfirm = 1 " +
            "and a.isActive= 1")
    Page<Ads> getAllAds(Pageable pageable);

    @Query("select a from Ads a")
    Page<Ads> getAllAdsAdmin(Pageable pageable);
}
