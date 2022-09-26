package com.wellbeignatwork.backend.service.Collaboration;

import com.github.prominence.openweathermap.api.model.onecall.current.CurrentWeatherData;
import com.sun.mail.iap.Response;

import com.wellbeignatwork.backend.entity.Collaboration.*;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.exceptions.Collaboration.BadRequestException;
import com.wellbeignatwork.backend.exceptions.Collaboration.ResourceNotFoundException;
import com.wellbeignatwork.backend.repository.Collaboration.*;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import com.wellbeignatwork.backend.service.UserService.MailService;
import com.wellbeignatwork.backend.util.FirebaseStorage;
import com.wellbeignatwork.backend.util.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;


@Service
public class OfferService implements IOfferService {
	@Autowired
	OfferRepository OfferRepo;

	@Autowired
	ICollaboration CollaborationRepo;

	@Autowired
	IPublicity PublicityRepo;
	@Autowired
	WeatherService weatherService;
	@Autowired
    UserRepository userRepository;
	@Autowired
	UsersOfferRepo usersOfferRepo;
	@Autowired
	MailService mailService;

	@Autowired
	ImageRepo imageRepo;

	@Autowired
	FirebaseStorage firebaseStorage;

	@Override
	public List<Offer> retrieveAllOffers() {
		return OfferRepo.findAll();

	}

	@Override
	public Offer addOffer(Offer o, long idCollaboration) {
		Collaboration collaboration = CollaborationRepo.findById(idCollaboration).get();
		o.setCollaboration(collaboration);
		return OfferRepo.save(o);
	}

	@Override
	public void deleteOffer(Long id) {
		OfferRepo.deleteById(id);
	}

	@Override
	public Offer updateOffer(Offer o) {
		return OfferRepo.save(o);
	}

	@Override
	public Offer retrieveOffer(Long id) {
		return OfferRepo.findById(id).orElse(null);

	}


	@Override
	@Transactional
	public float calculProm(long idOffer) {
		Offer o = OfferRepo.findById(idOffer).orElse(null);

			if (o.getHappy() == Happy.BLACK_FRIDAY) {
				o.setPromotion((o.getPrix() * 50) / 100);
			} else if (o.getHappy() == Happy.HAPPY_DAYS) {
				o.setPromotion((o.getPrix() * 20) / 100);
			} else if (o.getHappy() == Happy.HAPPY_HOUR) {
				o.setPromotion((o.getPrix() * 20) / 100);
			} else if(o.getHappy() == Happy.PROMOTION) {
				o.setPromotion((o.getPercentage() * o.getPrix()) / 100);
			}
			OfferRepo.save(o);
			return o.getPromotion();
	}
	public List<Offer> listAll() {
		return OfferRepo.findAll(Sort.by("idOffer").ascending());
	}

	@Override
	public Object getOfferWeather(Long idOffer) {
		Offer offer = OfferRepo.findById(idOffer).orElse(null);
		if (offer == null) {
			throw new ResourceNotFoundException("Offer is not exist");
		}
		//check if the Offer is already started
		if (LocalDate.now().isAfter(offer.getStarDateOf().toLocalDate())) {
			return new Response("No need to fetch weather of an Offer already started or finished", false);
		}
		CurrentWeatherData currentWeatherData = weatherService.getWeatherData(offer.getLocalisation());
		LocalDate nextWeek = LocalDate.now().plusDays(7);
		System.out.println(nextWeek);
		LocalDate offerStartDate = offer.getStarDateOf().toLocalDate();
		if (nextWeek.isBefore(offerStartDate) || nextWeek.isEqual(offerStartDate)) {
			return currentWeatherData.getCurrent();
		} else {
			int idx = 7 - Period.between(offerStartDate, nextWeek).getDays();
			return currentWeatherData.getDailyList().get(idx);
		}
	}

	@Override
	public void inviteUsersToOffer(List<Long> usersId, Long offerId) {
		Offer offer = OfferRepo.findById(offerId).orElse(null);
        if (offer == null) {
		throw new ResourceNotFoundException("Offer doesn't exist");
	}
        if (offer.getStarDateOf().isBefore(LocalDateTime.now())) {
		throw new BadRequestException("You can't invite someone to an Offer already started or finished");
	}
        for (Long userId : usersId) {
			User user = userRepository.findById(userId).orElse(null);
			if (user == null) break;
			boolean alreadyInvited = false;
			UsersOffer uo = usersOfferRepo.findByUserIdAndOfferIdOffer(userId, offerId);
			//Check if already this user has been invited
			if (uo != null) {
				if (!uo.getIsInvited()) {
					System.out.println(uo.getUser().getDisplayName() + " to invite");
					uo.setIsInvited(true);
					usersOfferRepo.save(uo);
				} else {
					alreadyInvited = true;
				}
			} else {
				UsersOffer usersOffer = new UsersOffer();
				usersOffer.setId(new UsersOfferKey(userId, offerId));
				usersOffer.setUser(user);
				usersOffer.setOffer(offer);
				usersOffer.setIsInvited(true);
				usersOfferRepo.save(usersOffer);
			}

			if (!alreadyInvited) {
				String inviteContent = "You have been invited to " +
						offer.getTitle() +
						".\n Venue : " +
						offer.getLocalisation() +
						"\n Start at : " +
						offer.getStarDateOf() +
						"\n Finish at : " +
						offer.getEndDateOf();

				//send mail and sms when inviting user
				mailService.sendMail(user.getEmail(), "You have been invited!", inviteContent, false);
				//smsService.sendSMS(inviteContent);
			}
		}
	}

	@Override
	public void uploadImageToOffer(MultipartFile img, Long idOffer) throws IOException {
		Offer offer = OfferRepo.findById(idOffer).orElse(null);
		if (offer == null) {
			throw new ResourceNotFoundException("Offer is not exist");
		}
		String name = firebaseStorage.uploadFile(img);
		Image image = new Image();
		image.setName("https://firebasestorage.googleapis.com/v0/b/"+firebaseStorage.getBUCKETNAME()+"/o/"+name+"?alt=media");
		imageRepo.save(image);
		offer.setImagesOffer(image);
		OfferRepo.save(offer);
	}
}


