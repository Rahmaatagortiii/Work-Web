import { Component, NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ChatRoomsComponent } from "./chat-rooms/chat-rooms.component";

export const routes: Routes = [
  {
    path: "",
    children: [{ path: "chat-rooms", component: ChatRoomsComponent }],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChattingRoutingModule {}
