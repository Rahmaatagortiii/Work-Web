import { User } from './../../Models/Collaboration/user';
import { Reservation } from './../../Models/Collaboration/Reservation';
import { Component, OnInit } from '@angular/core';
import { Offer } from '../../Models/Collaboration/offer';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['./add-reservation.component.scss']
})
export class AddReservationComponent implements OnInit {

  idUser: any;
  Reservation: Reservation = {
    idReservation: 0,
    startDateRes: new Date(),
    endDateRes: new Date(),
    nmPalce: 0,
    priceTotal: 0,
    userRes : new User,
    offersRes : new Offer
  }
file:File;
  r: Reservation[];

  title: any;
  button: any;
  idCollaboration: any;
  formCollaboration: FormGroup;

  constructor() { }

  ngOnInit(): void {
  }
  

}
