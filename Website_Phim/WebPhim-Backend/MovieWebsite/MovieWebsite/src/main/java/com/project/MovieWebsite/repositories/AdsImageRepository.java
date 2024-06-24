package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Ads;
import com.project.MovieWebsite.models.AdsImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdsImageRepository extends JpaRepository<AdsImage, Integer> {

    List<AdsImage> findByAds(Ads ads);
}
