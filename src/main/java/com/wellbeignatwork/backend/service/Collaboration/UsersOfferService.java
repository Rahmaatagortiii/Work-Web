package com.wellbeignatwork.backend.service.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.*;
import com.wellbeignatwork.backend.entity.User.User;

import com.wellbeignatwork.backend.exceptions.Collaboration.BadRequestException;
import com.wellbeignatwork.backend.exceptions.Collaboration.ResourceNotFoundException;

import com.wellbeignatwork.backend.repository.Collaboration.OfferRepository;
import com.wellbeignatwork.backend.repository.Collaboration.UsersOfferRepo;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersOfferService implements IUsersOfferService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    OfferRepository offerRepo;

    @Autowired
    UsersOfferRepo usersOfferRepo;

    @Override
    public void addOfferToFav(Long userId, Long offerId) {
        User user = userRepo.findById(userId).orElse(null);
        Offer offer = offerRepo.findById(offerId).orElse(null);
        if (offer == null) {
            throw new ResourceNotFoundException("Offer doesn't exist");
        }
        if (user != null) {
            UsersOffer usersOffer = getOrCreateUsersOffer(userId, offerId);
            usersOffer.setOffer(offer);
            usersOffer.setUser(user);
            usersOffer.setIsFavorite(true);
            usersOfferRepo.save(usersOffer );
        }
    }

    @Override
    public List<Offer> getFavOffer(Long userId) {
        List<UsersOffer> usersOffers = usersOfferRepo.findByUserIdAndIsFavoriteTrue(userId);
        List<Offer> offers = new ArrayList<>();
        usersOffers.forEach(uo -> offers.add(uo.getOffer()));
        return offers;
    }

    @Override
    public List<Offer> getInvitedOffers(Long userId) {
        List<UsersOffer> usersOffers = usersOfferRepo.findByUserIdAndIsInvitedTrue(userId);
        List<Offer> offers = new ArrayList<>();
        usersOffers.forEach(uo -> offers.add(uo.getOffer()));
        return offers;
    }

    @Override
    public List<Offer> getAcceptedOffers(Long userId) {
        List<UsersOffer> usersOffers = usersOfferRepo.findByUserIdAndIsAcceptedTrue(userId);
        List<Offer> offers = new ArrayList<>();
        usersOffers.forEach(uo -> offers.add(uo.getOffer()));
        return offers;
    }

    @Override
    public void acceptOffer(Long userId, Long offerId) {
        UsersOffer usersOffers = usersOfferRepo.findByUserIdAndOfferIdOffer(userId, offerId);
        if (usersOffers == null) {
            throw new BadRequestException("Cannot accept an Offer without invitation");
        }
        if (usersOffers.getIsAccepted()) {
            throw new BadRequestException("Your already accepted the invitation");
        }
        /*if (usersOffers.getOffer().getFees() > 0) {
            throw new BadRequestException("You must pay Offer fees first");
        }*/
        if (usersOffers.getIsInvited()) {
            usersOffers.setIsAccepted(true);
            usersOfferRepo.save(usersOffers);
        } else {
            throw new BadRequestException("Cannot accept an Offer without invitation");
        }
    }

    @Override
    public void feedbackOffer(Long userId, Long offerId, FeedBackCollaboration feedback) {
        UsersOffer usersOffer = usersOfferRepo.findByUserIdAndOfferIdOffer(userId, offerId);
        if (usersOffer == null) {
            throw new BadRequestException("Cannot give feedback for an Offer without participation");
        }
        LocalDateTime OfferEndDate = usersOffer.getOffer().getEndDateOf();
        LocalDateTime now = LocalDateTime.now();

        usersOffer.setFeedback(feedback.getContent());
        usersOffer.setRate(feedback.getRate());
        usersOfferRepo.save(usersOffer);
    }

    @Override
    public Float getAverageRateOffer(Long offerId) {
        if (!offerRepo.existsById(offerId)) {
            throw new ResourceNotFoundException("Offer doesn't exist");
        }
        return usersOfferRepo.getAverageRateOffer(offerId);
    }

    @Override
    public List<OfferFeedbacks> getFeedbackOffer(Long offerId) {
        if (!offerRepo.existsById(offerId)) {
            throw new ResourceNotFoundException("Offer doesn't exist");
        }
        List<UsersOffer> usersOffers = usersOfferRepo.findByOfferIdOfferAndAndIsAcceptedTrue(offerId);
        List<OfferFeedbacks> feedbacks = new ArrayList<>();
        for (UsersOffer ue : usersOffers) {
            OfferFeedbacks res = new OfferFeedbacks(ue.getUser().getId(), ue.getUser().getDisplayName());
            res.setRate(ue.getRate());
            res.setContent(ue.getFeedback());
            feedbacks.add(res);
        }
        return feedbacks;
    }

    public UsersOffer getOrCreateUsersOffer(Long userId, Long offerId) {
        UsersOffer usersOffer= usersOfferRepo.findByUserIdAndOfferIdOffer(userId, offerId);
        if (usersOffer != null) {
            return usersOffer;
        }
        usersOffer = new UsersOffer();
        usersOffer.setId(new UsersOfferKey(userId, offerId));

        return usersOffer;
    }
}
