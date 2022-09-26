import { User } from './user';
import { Offer } from './offer';
import { Image } from './Image';
export class Reservation {
    idReservation: Number;  
    startDateRes:Date; 
    endDateRes:Date;
    nmPalce:Number;
    priceTotal:Number;
    userRes :User;
    offersRes? : Offer;
}