package com.wellbeignatwork.backend.util;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.wellbeignatwork.backend.entity.Collaboration.Payment;
import com.wellbeignatwork.backend.entity.Collaboration.Reservation;
import com.wellbeignatwork.backend.entity.Collaboration.UsersOffer;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.repository.Collaboration.IReservationRepository;
import com.wellbeignatwork.backend.repository.Collaboration.UsersOfferRepo;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import com.wellbeignatwork.backend.service.Collaboration.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    IReservationRepository reservationRepository;
    @Autowired
    ReservationService reservationService;
    @Autowired
    UsersOfferRepo usersOfferRepo;



    @Value("sk_test_51Khl7ZAmmAEwNuySJwTRMgb230wvzoZdIK2y9TshyH9zw23VcRLJtZFu9X3oL4CHhPUUjdnwFZKs7i3GCsLYaAhI00CeUoUGzp")
    String stripeKey;

    public Payment payment(long idUser, long idReservation, Payment p) throws StripeException {
        Stripe.apiKey = stripeKey;
        User user = userRepository.findById(idUser).get();
        Reservation reservation = reservationRepository.findById(idReservation).get();
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getDisplayName());
        params.put("email", user.getEmail());
        params.put("amount*100", reservation.getPriceTotal());
        //params.put("created",p.getCreated());
        Customer customer = Customer.create(params);
        p.setCustomerId(customer.getId());
        return p;
    }

    public Reservation createCharge(String email, String token, Long idUser, Long idReservation, Long idOffer) throws StripeException, MessagingException {
        UsersOffer user = usersOfferRepo.findByUserIdAndOfferIdOffer(idUser,idOffer);
        Reservation reservation = reservationRepository.findById(idReservation).get();
        String id;
        Stripe.apiKey = stripeKey;
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", Math.round(reservation.getPriceTotal() * 100));
        chargeParams.put("currency", "usd");
        chargeParams.put("receipt_email", email);
        chargeParams.put("description", "Charge for " + email);
        chargeParams.put("source", token); // ^ obtained with Stripe.js
        //create a charge
        Charge charge = Charge.create(chargeParams);
        id = charge.getId();
        if (id == null) {
            throw new RuntimeException("Something went wrong!");
        }

        // payment successfully
           return reservation ;
    }

}