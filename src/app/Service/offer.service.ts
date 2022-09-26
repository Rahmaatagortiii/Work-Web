import { Offer } from './../Models/Collaboration/offer';
import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class OfferService {

  
  offre : Offer;
  constructor(private http :HttpClient) { }
  $eventEmit = new EventEmitter();
  getAllOffer():Observable<Offer[]>{
    return this.http.get<Offer[]>("http://localhost:8081/Wellbeignatwork/Offer/retrieveAllOffers/")
  }
  getOfferById( idOffer : number){
    return this.http.get<Offer>("http://localhost:8081/Wellbeignatwork/Offer/retrieveOffer/"+idOffer)
  }
  updateOffer(offer: Offer){
    return this.http.put("http://localhost:8081/Wellbeignatwork/Offer/updateOffer",offer)
  }

  addOffer(idCollaboration : number,offer :Offer){
    return this.http.post("http://localhost:8081/Wellbeignatwork/Offer/addOffer/"+idCollaboration,offer)
  }

  deleteOffer(idOffer: number){
    return this.http.delete("http://localhost:8081/Wellbeignatwork/Offer/deleteOffer/"+idOffer)
  }
  uploadImageToOffer(form:FormData , idOffer : number ){
    return this.http.post("http://localhost:8081/Wellbeignatwork/Offer/uploadImageToOffer/"+idOffer, form)
     }

     sendEventData(idOffer : number):any{
      
      this.getOfferById(idOffer).pipe(take(1)).subscribe(x=>{
        console.log(x.title);
        this.offre=x;
        this.$eventEmit.emit(this.offre);
        return x;
      });
    }
}
