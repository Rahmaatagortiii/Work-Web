import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Collaboration } from '../model/collaboration';

@Injectable({
  providedIn: 'root'
})
export class CollaborationService {

  constructor(private http : HttpClient) { }

  getAllColaboration() :Observable<Collaboration[]>{
    return this.http.get<Collaboration[]>("http://localhost:8085/Collaboration/Collaboration/retrieveAllCollaborations")
  }
  getCollaborationById( idCollaboration : number){
    return this.http.get("http://localhost:8085/Collaboration/Collaboration/retrieveCollaboration/"+idCollaboration)
  }
  updateCollaboration(Collaboration: Collaboration){
    return this.http.put("http://localhost:8085/Collaboration/Collaboration/updateCollaboration/",Collaboration)
  }

  addCollaboration(idUser : number,Collaboration :Collaboration){
    return this.http.post("http://localhost:8085/Collaboration/addCollaboration/"+idUser+Collaboration,Collaboration)
  }

  deleteCollaboration(idCollaboration: number){
    return this.http.delete("http://localhost:8085/Collaboration/Collaboration/deleteCollaboration/"+idCollaboration)
  }
}
