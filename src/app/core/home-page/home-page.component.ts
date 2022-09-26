import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Publicity } from '../../Model/Publicity';
import { PublicityService } from '../../service/Publicity.service';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

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
  }it(): void {
  }

}
