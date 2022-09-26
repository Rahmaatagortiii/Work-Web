package com.wellbeignatwork.backend.repository.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface IReservation extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByStartDateResIsBefore(LocalDateTime date);

	
}
