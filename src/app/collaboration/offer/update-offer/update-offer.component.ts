import { Component, OnInit } from '@angular/core';
import { Collaboration } from '../../../Models/Collaboration/collaboration';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OfferService } from '../../../Service/offer.service';
import { Offer } from '../../../Models/Collaboration/offer';
import { CollaborationService } from '../../../Service/collaboration.service';
@Component({
  selector: 'app-update-offer',
  templateUrl: './update-offer.component.html',
  styleUrls: ['./update-offer.component.scss']
})
export class UpdateOfferComponent implements OnInit {
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

  constructor(private formBuilder: FormBuilder, private ActivatedRoute: ActivatedRoute, router: Router,
    private OfferService: OfferService, private CollaborationService: CollaborationService) { }

    collaborations: Collaboration[] = [];
    selectedcollaborationId: any;
  ngOnInit(): void {
    this.CollaborationService.getAllColaboration().subscribe(collaborations => {
      this.collaborations = collaborations
      console.log(collaborations)
    })
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
  updateOffer() {
    console.log(this.formdata.get('image'))
    console.log(this.file);
    console.log(this.offer);
    this.offer.collaboration = undefined;
    this.offer.imagesOffer = undefined;
    this.OfferService.updateOffer(this.offer).subscribe(o => {
      console.log(o)
      this.OfferService.uploadImageToOffer(this.formdata,JSON.parse(JSON.stringify(o)).idOffer).subscribe(data => window.alert("image uploaded successfully"),(error)=>console.log(error))})
   }
}
