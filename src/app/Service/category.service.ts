import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private _http: HttpClient) {}
  //load all the cateogries
  public categories() {
    return this._http.get(`http://localhost:8081/Wellbeignatwork/category/`);
  }

  //add new category
  public addCategory(category:any) {
    return this._http.post(`http://localhost:8081/Wellbeignatwork/category/`, category);
  }
}
