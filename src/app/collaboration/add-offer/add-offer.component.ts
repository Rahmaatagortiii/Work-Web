import { CollaborationService } from './../../Service/collaboration.service';
import { Collaboration } from './../../Models/Collaboration/collaboration';
import { Component, OnInit } from '@angular/core';
import { OfferService } from '../../Service/offer.service';
import { Offer } from '../../Models/Collaboration/offer';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';



@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.scss']
})
export class AddOfferComponent implements OnInit {
  idCollaboration: any;
  offer: Offer = {
    idOffer: 0,
    title: '',
    descrption: '',
    starDateOf: '',
    endDateOf: '',
    nplaces: 0,
    promotion: 0,
    percentage: 0,
    localisation: '',
    prix: 0,
    imagesOffer: {
      id: 0,
      name: ''
    },
    happy: '',
    collaboration: new Collaboration
  };
  file: File;
  o: Offer[];

  title: any;
  button: any;
  idOffer: any;
  formdata : FormData = new FormData();
  formOffer: FormGroup;
  captcha : string;
  email : string;
  constructor(private formBuilder: FormBuilder, private ActivatedRoute: ActivatedRoute, router: Router,
    private OfferService: OfferService, private CollaborationService: CollaborationService) {
      this.captcha = '';
      this.email = 'homrani.mahdi1998@gmail.com'
     }

  collaborations: Collaboration[] = [];
  selectedcollaborationId: any;
  ngOnInit(): void {
    this.CollaborationService.getAllColaboration().subscribe(collaborations => {
      this.collaborations = collaborations
      console.log(collaborations)
    })
  }

  resolved(captchaResponse:string){
    this.captcha =captchaResponse;
    console.log('resolved captcha witch response : ' + this.captcha);
  }
  onSelectCollaboration(event: any) {
    this.selectedcollaborationId = event.target.value;
    //console.log(this.selectedcollaboration);
    console.log(event.target.value)
  }

  onFileSelected(event:any){
    this.file=event.target.files[0];
    //console.log(this.file)
    this.formdata.append('image', this.file, this.file.name);
    //console.log(this.formdata.get('image'))
  }
  addOffer() {
    Swal.fire('Success ', 'Offer Added', 'success');
    console.log(this.formdata.get('image'))
    console.log(this.file);
    console.log(this.offer);
    this.offer.collaboration = undefined;
    this.offer.imagesOffer = undefined;
    this.OfferService.addOffer(this.selectedcollaborationId, this.offer).subscribe(o => {
      console.log(o)
      this.OfferService.uploadImageToOffer(this.formdata,JSON.parse(JSON.stringify(o)).idOffer).subscribe(data => window.alert("image uploaded successfully"),(error)=>console.log(error))}
      ,(error) => {
        Swal.fire('Error', 'Error in adding Offer', 'error');
      })
  
    }
   
}


