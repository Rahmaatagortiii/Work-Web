import { Publicity } from '../Model/Publicity';
import { EventEmitter, Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PublicityService {

  Publicity: Publicity
  constructor(private http : HttpClient) { }
  $eventEmit = new EventEmitter();

  addPublicity(idoffer : number ,Publicity :Publicity){
    return this.http.post("http://localhost:8081/Wellbeignatwork/Publicity/addPublicity/"+idoffer,Publicity)
  }
  getAllPublicity() :Observable<Publicity[]>{
    return this.http.get<Publicity[]>("http://localhost:8081/Wellbeignatwork/Publicity/retriveAllPub")
  }
  getPublicitById( idPublicity : number){
    return this.http.get<Publicity>("http://localhost:8081/Wellbeignatwork/Publicity/retrievePublicity/"+idPublicity)
  }
  uploadImageToPublicity(form:FormData , idPublicity : number ){

    return this.http.post("http://localhost:8081/Wellbeignatwork/Publicity/upload-image/"+idPublicity, form)
     }
     uploadImageToPub(form:FormData , idPublicity : number ){
      return this.http.post("http://localhost:8081/Wellbeignatwork/Publicity/uploadImageToPub/"+idPublicity, form)
       }
     
}
