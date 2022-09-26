package com.wellbeignatwork.backend.repository.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Publicity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPublicity extends JpaRepository<Publicity, Long> {
    List<Publicity> findByOffersTitle(String title);
    @Query("SELECT p FROM Publicity p WHERE p.offers.starDateOf <= ?1")
    List<Publicity> getPublicityBeforeOfferStartDate(LocalDateTime date);
    List<Publicity> findByOffersLocalisation(String loc);
}
