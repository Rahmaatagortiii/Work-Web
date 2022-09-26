import { Collaboration } from './../../Models/Collaboration/collaboration';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'collabSearchFilter'
})
export class CollabSearchFilterPipe implements PipeTransform {

  transform(collabs:Collaboration[],searchValue:string): Collaboration[] {
    if(!collabs || !searchValue){
        return collabs;
    }
    return collabs.filter(collab=>collab.name.toLowerCase().includes(searchValue.toLowerCase()))
  }

}
