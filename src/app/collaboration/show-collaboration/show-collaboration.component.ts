import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Collaboration } from '../../Models/Collaboration/collaboration';
import { CollaborationService } from '../../Service/collaboration.service';

@Component({
  selector: 'app-show-collaboration',
  templateUrl: './show-collaboration.component.html',
  styleUrls: ['./show-collaboration.component.scss']
})
export class ShowCollaborationComponent implements OnInit {

  collaboration : Collaboration;
  file:File;
Collaboration : Collaboration = new Collaboration;
  title: any;
  button: any;
  idCollaboration: any;
  formCollaboration: FormGroup;
  
  constructor(private activatedRoute: ActivatedRoute,private dialog :MatDialog, router: Router,@Inject(CollaborationService)
  private collaborationService: CollaborationService , @Inject(MAT_DIALOG_DATA) public data:Collaboration) { }

  ngOnInit(): void {
    this.collaborationService.$eventEmit.subscribe((data:Collaboration)=> {
      this.Collaboration=data;
      this.getCollabById(data.idCollaboration);
      console.log(this.Collaboration);
    })
  }
  showCollaboration(){
    this.collaborationService.getCollaborationById(this.idCollaboration);
  }
  getCollabById(id : number){
    this.collaborationService.getCollaborationById(id).subscribe(Collab=>console.log(Collab))
  }
}
