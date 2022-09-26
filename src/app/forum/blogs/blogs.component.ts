import { Component, OnInit } from '@angular/core';
import { Post } from '../../Models/Forum/Post';
import { BlogsServiceService } from './blogs-service.service';

@Component({
  selector: 'app-blogs',
  templateUrl: './blogs.component.html',
  styleUrls: ['./blogs.component.scss']
})
export class BlogsComponent implements OnInit {

  listPosts : Post[];
  constructor(private _service:BlogsServiceService) { }
  ngOnInit(): void {
    this._service.getAllPost().subscribe
    (res=>
      {
        console.log(res);
      this.listPosts=res
      }
    );
    this._service.getPostReactions(1).subscribe(res=>console.log(res));
    
  }
  deleteBlog(id:number){
    this._service.deleteBlogById(id).subscribe(()=>this._service.getAllPost().subscribe(res=>{this.listPosts=res}));
  }
  getnbreactionByblog(id:number){
    this._service.getPostReactions(id).subscribe(res=>console.log(res));
  }

}
