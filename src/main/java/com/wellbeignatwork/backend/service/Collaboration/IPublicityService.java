package com.wellbeignatwork.backend.service.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Publicity;
import org.apache.tomcat.jni.Local;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface IPublicityService {
    List<Publicity> retrieveAllPublicitys();

    Publicity addPublicity(Publicity p,long idOffer);

    void deletePublicity(Long id);

    Publicity updatePublicity(Publicity p);

    Publicity retrievePublicity(Long id);

    public boolean dateOffer(long idPublicity, Date starDateOf, Date finDateOf);

    void uploadImageToPulicity(MultipartFile img, Long idPub) throws IOException;

    void uploadImageToPub(MultipartFile file, Long eventId) throws IOException;
    void uploadPubBanner(MultipartFile file, Long eventId) throws IOException;
    void deleteImage(String imgName);
    List<Publicity> retrieveByTitle(String title);
    List<Publicity> retrieveBeforeOfferStartDate();
    List<Publicity> retrieveByOfferLocalisation(String loc);
}
