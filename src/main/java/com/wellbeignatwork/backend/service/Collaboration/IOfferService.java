package com.wellbeignatwork.backend.service.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Offer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IOfferService {
    List<Offer> retrieveAllOffers();

    Offer addOffer(Offer o,long idCollaboration);

    void deleteOffer(Long id);

    Offer updateOffer(Offer o);

    Offer retrieveOffer(Long id);

    List<Offer> listAll() ;
    
    float calculProm(long idOffer);


    Object getOfferWeather(Long idOffer);

    void inviteUsersToOffer(List<Long> usersId, Long offerId);

    public void uploadImageToOffer(MultipartFile img, Long idCollaboration)  throws IOException ;
}
