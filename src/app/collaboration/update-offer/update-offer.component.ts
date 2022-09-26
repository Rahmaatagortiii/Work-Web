import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Offer } from '../../Models/Collaboration/offer';
import { OfferService } from '../../Service/offer.service';

@Component({
  selector: 'app-update-offer',
  templateUrl: './update-offer.component.html',
  styleUrls: ['./update-offer.component.scss']
})
export class UpdateOfferComponent implements OnInit {

  file:File;
  Offre : Offer = new Offer;
  title: any;
  button: any;
  idOffre: any;
  formdata : FormData = new FormData();
  formOffer: FormGroup;

  constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute, private dialog :MatDialog,router: Router,@Inject(OfferService)
    private OfferService: OfferService) { }

  ngOnInit(): void {
    this.OfferService.$eventEmit.subscribe((data)=> {
      this.Offre=data;
      console.log(this.Offre);
    })
  }
  onFileSelected(event:any){
    this.file=event.target.files[0];
    console.log(this.file)
  }
  updateOffer(){
    console.log(this.file);
    console.log(this.Offre)



    this.OfferService.updateOffer(this.Offre).subscribe(offre =>
       {
         if (this.file){
           let formdata = new FormData();
           formdata.append('image',this.file,this.file.name);
           this.OfferService.uploadImageToOffer(formdata,this.Offre.idOffer).subscribe(
             {
               next : (data)=>console.log(data),
               error: (error)=>console.log(error),
               complete : ()=>{
                 window.location.reload();
                 this.dialog.closeAll();
               }
             }
           )

         }else {
          window.location.reload();
          this.dialog.closeAll();
         }
    })}

  getCollabById(id : number){
this.OfferService.getOfferById(id).subscribe(offre=>console.log(offre))

  }

}
