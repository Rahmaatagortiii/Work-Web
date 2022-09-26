package com.wellbeignatwork.backend.service.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.Reservation;
import javax.mail.MessagingException;
import java.util.List;

public interface IReservationService {
	
	   Reservation reservation(long idUser, long idOffer, Reservation r) throws MessagingException;
	   float prixTotale (long idUser,long idReservation);
	   List<Reservation> findAll();
	List<Reservation> listAll() ;

}
