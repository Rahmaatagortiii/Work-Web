import { ElementRef,Component, OnInit, ViewChild, Inject } from '@angular/core';
import { Offer } from '../../Models/Collaboration/offer';
import { jsPDF } from "jspdf";
import html2canvas from 'html2canvas';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { OfferService } from '../../Service/offer.service';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-pdf-offers',
  templateUrl: './pdf-offers.component.html',
  styleUrls: ['./pdf-offers.component.scss']
})
export class PdfOffersComponent implements OnInit {
  Offre: any;
  @ViewChild('content', { static: false }) el: ElementRef;
  o : Offer[];

  constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute, private dialog :MatDialog,router: Router,@Inject(OfferService)
    private OfferService: OfferService) { }

    ngOnInit(): void {
      this.OfferService.$eventEmit.subscribe((data)=> {
        this.Offre=data;
        console.log(this.Offre);
      })
    }

  openPDF(): void {
    let DATA: any = document.getElementById('htmlData');
    html2canvas(DATA).then((canvas) => {
      let fileWidth = 208;
      let fileHeight = (canvas.height * fileWidth) / canvas.width;
      const FILEURI = canvas.toDataURL('image/png');
      let PDF = new jsPDF('p', 'mm', 'a4');
      let position = 0;
      PDF.addImage(FILEURI, 'PNG', 0, position, fileWidth, fileHeight);
      PDF.save('AllOFfer.pdf');
    });
  }
}


