package com.wellbeignatwork.backend.service.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.FeedBackCollaboration;
import com.wellbeignatwork.backend.entity.Collaboration.Offer;
import com.wellbeignatwork.backend.entity.Collaboration.OfferFeedbacks;


import java.util.List;

public interface IUsersOfferService {
    // remove the user id when you can get the current connected user

    void addOfferToFav(Long userId, Long offerId);

    void acceptOffer(Long userId, Long offerId);

    List<Offer> getFavOffer(Long userId);

    List<Offer> getInvitedOffers(Long userId);

    List<Offer> getAcceptedOffers(Long userId);
    List<OfferFeedbacks> getFeedbackOffer(Long offerId);
    Float getAverageRateOffer(Long offerId);
    void feedbackOffer(Long userId, Long offerId, FeedBackCollaboration feedback);
}
