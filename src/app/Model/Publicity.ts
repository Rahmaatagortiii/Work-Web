import { Offer } from './offer';
import { Image } from './Image';
export class Publicity {
    idPublicity: Number;  
    title:string; 
    description:string;
    starDatePub:Date;
    endDatePub:Date;
    imagesPublicity:Image;
    offer? : Offer;
}