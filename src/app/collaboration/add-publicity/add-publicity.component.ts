import { OfferService } from './../../Service/offer.service';
import { PublicityService } from './../../Service/Publicity.service';
import { Offer } from './../../Models/Collaboration/offer';
import { Publicity } from './../../Models/Collaboration/Publicity';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-publicity',
  templateUrl: './add-publicity.component.html',
  styleUrls: ['./add-publicity.component.scss']
})
export class AddPublicityComponent implements OnInit {

  idUser: any;
  publicity: Publicity = {
    starDatePub: new Date(),
    endDatePub: new Date(),
    description: '',
    title: '',
    idPublicity: 0,
    imagesPublicity: {
      id: 0,
      name: ''
    },
    offer: new Offer
  }
file:File;
  p: Publicity[];

  title: any;
  button: any;
  idOffer: any;
  formdata : FormData = new FormData();
  formOffer: FormGroup;

  constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute, router: Router,
    private OfferService: OfferService,private PublicityService: PublicityService, ) { }

    offers: Offer[] = [];
    selectedOfferId: any;
  ngOnInit(): void {
    this.OfferService.getAllOffer().subscribe(offers => {
      this.offers = offers
      console.log(offers)
    })
  }

  onSelectOffer(event: any) {
    this.selectedOfferId = event.target.value;
    //console.log(this.selectedcollaboration);
    console.log(event.target.value)
  }

  onFileSelected(event:any){
    this.file=event.target.files[0];
    //console.log(this.file)
    this.formdata.append('image', this.file, this.file.name);
    //console.log(this.formdata.get('image'))
  }

  addPublicity() {
    Swal.fire('Success ', 'Publicity Added', 'success');
    console.log(this.formdata.get('image'))
    console.log(this.file);
    console.log(this.publicity);
    this.publicity.offer = undefined;
    this.publicity.imagesPublicity = undefined;
    this.PublicityService.addPublicity(this.selectedOfferId, this.publicity).subscribe(o => {
      console.log(o)
      this.PublicityService.uploadImageToPub(this.formdata,JSON.parse(JSON.stringify(o)).idOffer).subscribe(data => window.alert("image uploaded successfully"),(error)=>console.log(error))}
      ,(error) => {
        Swal.fire('Error', 'Error in adding Publicity', 'error');
      })
  
    }

}
