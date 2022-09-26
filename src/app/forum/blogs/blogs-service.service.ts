import { Injectable } from '@angular/core';
import { HttpClient } from'@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from '../../Models/Forum/Post';
@Injectable({
  providedIn: 'root'
})
export class BlogsServiceService {

  
  constructor(private _http:HttpClient) { }
  
  getAllPost():Observable<Post[]>{
    return this._http.get<Post[]>("http://localhost:8081/Wellbeignatwork/Post/all-post")
  }
  deleteBlogById(id:number){
    return this._http.delete("http://localhost:8081/Wellbeignatwork/Post/delete-post/"+id);
  }
  getPostReactions(id:number){
    return this._http.get<number>("http://localhost:8081/Wellbeignatwork/React/getNbrReactionByPost/"+id);
  }
}
