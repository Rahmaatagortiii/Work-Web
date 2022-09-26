import { PdfOffersComponent } from './../pdf-offers/pdf-offers.component';
import { ShowOffersComponent } from './../show-offers/show-offers.component';
import { UpdateOfferComponent } from './../update-offer/update-offer.component';
import { OfferService } from './../../Service/offer.service';
import { Offer } from './../../Models/Collaboration/offer';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { jsPDF } from "jspdf";

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.scss']
})
export class OfferComponent implements OnInit {
  o : Offer[];
  Offer : Offer;
  constructor(private OfferService: OfferService,private matDialog:MatDialog,private router : Router ) { }
  @ViewChild('content', { static: false }) el: ElementRef;
  totalLentgh:any;
  page:number = 1;
  ngOnInit(): void {
    this.OfferService.getAllOffer().subscribe(
      (data)=>{ 
        console.log(data);
        this.o=data
        this.totalLentgh = data.length 
      });
  }

  viewCollaboration(idOffer:number){
    let collaboration;
    for (let i=0;i<this.o.length;i++) {
      if (this.o[i].idOffer == idOffer){
        collaboration= this.o[i];
      }
      this.router.navigate([`viewOffer/${idOffer}`])
    }
  }

  deleteOffer(idOffer:number){
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })
    
    swalWithBootstrapButtons.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, cancel!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.OfferService.deleteOffer(idOffer)
        .subscribe(()=>this.OfferService.getAllOffer().subscribe(res=>{this.o=res}));
        swalWithBootstrapButtons.fire(
          'Deleted!',
          'Your file has been deleted.',
          'success'
        )
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          'Cancelled',
          'Your imaginary file is safe :)',
          'error'
        )
      }
    })
  }
  updateOffer(idCollaboration:number){
    
    this.Offer=this.OfferService.sendEventData(idCollaboration);
    this.matDialog.open(UpdateOfferComponent);
    }
    showOffer(Offer:Offer){
  
      this.matDialog.open(ShowOffersComponent ,{data :Offer});
      }

      openPDF(idCollaboration:number){
        this.Offer=this.OfferService.sendEventData(idCollaboration);
        this.matDialog.open(PdfOffersComponent);
      };
      

}
