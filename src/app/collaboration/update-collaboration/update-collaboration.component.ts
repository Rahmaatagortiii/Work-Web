import { Collaboration } from './../../Models/Collaboration/collaboration';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CollaborationService } from '../../Service/collaboration.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-update-collaboration',
  templateUrl: './update-collaboration.component.html',
  styleUrls: ['./update-collaboration.component.scss']
})
export class UpdateCollaborationComponent implements OnInit {

file:File;
Collaboration : Collaboration = new Collaboration;
  title: any;
  button: any;
  idCollaboration: any;
  formCollaboration: FormGroup;

  constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute,private dialog :MatDialog, router: Router,@Inject(CollaborationService)
    private collaborationService: CollaborationService) { }

  ngOnInit(): void {
    this.collaborationService.$eventEmit.subscribe((data:Collaboration)=> {
      this.Collaboration=data;
      this.getCollabById(data.idCollaboration);
      console.log(this.Collaboration);
    })
  }
  onFileSelected(event:any){
    this.file=event.target.files[0];
    console.log(this.file)
  }
  updateCollaboration(){
    console.log(this.file);
    console.log(this.Collaboration)
    this.collaborationService.updateCollaboration(this.Collaboration).subscribe(collab =>
       {
         if (this.file){
           let formdata = new FormData();
           formdata.append('image',this.file,this.file.name);
           this.collaborationService.uploadImageToCollabotration(formdata,this.Collaboration.idCollaboration).subscribe(
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
this.collaborationService.getCollaborationById(id).subscribe(Collab=>console.log(Collab))

  }
}


