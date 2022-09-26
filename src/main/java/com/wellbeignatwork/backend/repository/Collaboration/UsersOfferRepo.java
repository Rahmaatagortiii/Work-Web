package com.wellbeignatwork.backend.repository.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.UsersOffer;
import com.wellbeignatwork.backend.entity.Collaboration.UsersOfferKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface UsersOfferRepo extends JpaRepository<UsersOffer, UsersOfferKey> {

    List<UsersOffer> findByUserIdAndIsFavoriteTrue(Long userId);

    List<UsersOffer> findByUserIdAndIsInvitedTrue(Long userId);

    List<UsersOffer> findByUserIdAndIsAcceptedTrue(Long userId);
    List<UsersOffer> findByOfferAndAndIsInvitedTrue(Long idOffer);
    List<UsersOffer> findByOfferIdOfferAndAndIsAcceptedTrue(Long idOffer);
    UsersOffer findByUserIdAndOfferIdOffer(Long userId, Long idOffer);

    @Query("SELECT o FROM UsersOffer o WHERE o.isAccepted = true AND o.offer.starDateOf >= ?1 AND o.offer.starDateOf <= ?2")
    List<UsersOffer> getByOfferStartDate(LocalDateTime inf, LocalDateTime sup);

    @Query("SELECT o FROM UsersOffer o WHERE o.isAccepted = true AND o.offer.endDateOf >= ?1 AND o.offer.endDateOf <= ?2")
    List<UsersOffer> getByOfferEndDate(LocalDateTime inf, LocalDateTime sup);

   @Query("SELECT AVG(o.rate) FROM UsersOffer as o WHERE o.id.offerId = ?1 AND o.rate IS NOT NULL")
    Float getAverageRateOffer(Long eventId);
}
