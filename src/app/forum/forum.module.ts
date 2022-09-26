import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ForumRoutingModule, routes } from './forum-routing.module';
import { BlogsComponent } from './blogs/blogs.component';
import { RouterModule } from '@angular/router';
import { ComponentsModule } from "../component/component.module";

@NgModule({
  declarations: [
    BlogsComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ComponentsModule
  ]
})
export class ForumModule { }
