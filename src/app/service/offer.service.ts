import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Offer } from '../model/offer';

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  constructor(private http :HttpClient) { }

  getAllOffer():Observable<Offer[]>{
    return this.http.get<Offer[]>("http://localhost:8085/Offer/retrieveAllOffers/")
  }
  getOfferById( idOffer : number){
    return this.http.get("http://localhost:8085/Offer/retrieveOffer/"+idOffer)
  }
  updateOffer(offer: Offer){
    return this.http.put("http://localhost:8085/Offer/updateOffer/",offer)
  }

  addOffer(idOffer : number,offer :Offer){
    return this.http.post("http://localhost:8085/Offer/addOffer/"+idOffer,offer)
  }

  deleteOffer(idOffer: number){
    return this.http.delete("http://localhost:8085/Offer/deleteOffer/"+idOffer)
  }
}
