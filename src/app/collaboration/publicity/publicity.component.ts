import { PublicityService } from './../../Service/Publicity.service';
import { Publicity } from './../../Models/Collaboration/Publicity';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-publicity',
  templateUrl: './publicity.component.html',
  styleUrls: ['./publicity.component.scss']
})
export class PublicityComponent implements OnInit {
  p : Publicity[];
  publicity : Publicity;
  constructor(private PublicityService: PublicityService,private matDialog:MatDialog,private router : Router ) { }
  totalLentgh:any;
  page:number = 1;
  ngOnInit(): void {
    this.PublicityService.getAllPublicity().subscribe(
      (data)=>{ 
        console.log(data);
        this.p=data
        this.totalLentgh = data.length 
      });
  }

  viewPublicity(idPublicity:number){
    let offer;
    for (let i=0;i<this.p.length;i++) {
      if (this.p[i].idPublicity == idPublicity){
        offer= this.p[i];
      }
    }
  }

  
}
