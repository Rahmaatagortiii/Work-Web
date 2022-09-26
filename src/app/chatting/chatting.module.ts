import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { ChattingRoutingModule, routes } from "./chatting-routing.module";
import { ChatRoomsComponent } from "./chat-rooms/chat-rooms.component";
import { RouterModule } from "@angular/router";
import { TableComponent } from "../component/table/table.component";
import { ComponentsModule } from "../component/component.module";

@NgModule({
  declarations: [ChatRoomsComponent],
  imports: [CommonModule, RouterModule.forChild(routes), ComponentsModule],
})
export class ChattingModule {}
