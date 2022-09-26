import { OfferService } from '../../Service/offer.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Offer } from '../../Models/Collaboration/offer';

@Component({
  selector: 'app-show-offers',
  templateUrl: './show-offers.component.html',
  styleUrls: ['./show-offers.component.scss']
})
export class ShowOffersComponent implements OnInit {
  Offer : Offer = new Offer;
  constructor(private activatedRoute: ActivatedRoute,private dialog :MatDialog, router: Router,@Inject(OfferService)
  private OfferService: OfferService , @Inject(MAT_DIALOG_DATA) public data:Offer) { }

  ngOnInit(): void {
    this.OfferService.$eventEmit.subscribe((data:Offer)=> {
      this.Offer=data;
      this.getOffreById(data.idOffer);
      console.log(this.Offer);
    })
  }
  showCollaboration(){
    this.OfferService.getOfferById(this.Offer.idOffer);
  }
  getOffreById(id : number){
    this.OfferService.getOfferById(id).subscribe(offre=>console.log(offre))
  }

}
