import { Collaboration } from './../Models/Collaboration/collaboration';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { EventEmitter, Injectable, Output } from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class CollaborationService {

  Collaboration : Collaboration;
  constructor(private http : HttpClient) { }
  $eventEmit = new EventEmitter();
  getAllColaboration() :Observable<Collaboration[]>{
    return this.http.get<Collaboration[]>("http://localhost:8081/Wellbeignatwork/Collaboration/retrieveAllCollaborations")
  }
  getCollaborationById( idCollaboration : number){
    return this.http.get<Collaboration>("http://localhost:8081/Wellbeignatwork/Collaboration/retrieveCollaboration/"+idCollaboration)
  }
  updateCollaboration(Collaboration: Collaboration){
    return this.http.put("http://localhost:8081/Wellbeignatwork/Collaboration/UpdateCollaboration",Collaboration)
  }

  addCollaboration(Collaboration :Collaboration){
    return this.http.post("http://localhost:8081/Wellbeignatwork/Collaboration/addCollaboration",Collaboration)
  }

  deleteCollaboration(idCollaboration: number){
    return this.http.delete("http://localhost:8081/Wellbeignatwork/Collaboration/deleteCollaboration/"+idCollaboration)
  }
  uploadImageToCollabotration(form:FormData , idCollaboration : number ){

 return this.http.post("http://localhost:8081/Wellbeignatwork/Collaboration/uploadImageToCollabotration/"+idCollaboration, form)
  }

  sendEventData(idCollaboration : number):any{
    this.getCollaborationById(idCollaboration).pipe(take(1)).subscribe(x=>{
      console.log(x.name);
      this.Collaboration=x;
      this.$eventEmit.emit(this.Collaboration);
      return x;
    });
  }
}
