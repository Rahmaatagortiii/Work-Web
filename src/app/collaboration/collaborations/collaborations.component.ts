import { ShowCollaborationComponent } from './../show-collaboration/show-collaboration.component';
import { Collaboration } from './../../Models/Collaboration/collaboration';
import { Post } from './../../Models/Forum/Post';
import { Component, EventEmitter, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CollaborationService } from '../../Service/collaboration.service';
import {NgxPaginationModule} from 'ngx-pagination';
import { UpdateCollaborationComponent } from '../update-collaboration/update-collaboration.component';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-collaborations',
  templateUrl: './collaborations.component.html',
  styleUrls: ['./collaborations.component.scss']
})
export class CollaborationsComponent implements OnInit {
  c : Collaboration[];
  Collaboration : Collaboration;
  $eventEmit = new EventEmitter();
  constructor(private collaborationService: CollaborationService,private matDialog:MatDialog,private router : Router) { }
  searchValue: string = "";
  fields = ["", "name", "description", "email", "date"];
  totalLentgh:any;
  page:number = 1;
  ngOnInit(): void {
    this.collaborationService.getAllColaboration().subscribe(
      (data)=>{ 
        console.log(data);
        this.c=data
        this.totalLentgh = data.length 
      });
  }

  deleteCollabora(idCollaboration:number){
    this.collaborationService.deleteCollaboration(idCollaboration).subscribe(()=>
     this.collaborationService.getAllColaboration().
      subscribe(data=>{this.c}));
      }


      editCollaboration(idCollaboration:number){
        let collaboration;
        for (let i=0;i<this.c.length;i++) {
          if (this.c[i].idCollaboration == idCollaboration){
            collaboration= this.c[i];
          }
          this.router.navigate([`editCollaboration/${idCollaboration}`])
        }
      }


      deleteCollaboration(idCollaboration:number){
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
            this.collaborationService.deleteCollaboration(idCollaboration)
            .subscribe(()=>this.collaborationService.getAllColaboration().subscribe(res=>{this.c=res}));
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

      updateCollaboration(idCollaboration:number){
    
        this.Collaboration=this.collaborationService.sendEventData(idCollaboration);
        this.matDialog.open(UpdateCollaborationComponent);
        }

        showCollaboration(collaboration:Collaboration){
  
          this.matDialog.open(ShowCollaborationComponent ,{data :collaboration});
          }
}
