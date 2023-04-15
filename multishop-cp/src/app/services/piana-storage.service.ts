import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PianaStorageService {

  constructor() { }

  putObject(key: string, obj: any){
    localStorage.setItem(key, JSON.stringify(obj));
  }

  getObject(key: string): any {
    console.log(localStorage.length)
    return JSON.parse(localStorage.getItem(key));
  }

  getFieldValue(key: string, field: string) {
    return JSON.parse(localStorage.getItem(key))[field];
  }

  removeObject(key: string){
    localStorage.removeItem(key);
  }

  clear() {
    localStorage.clear();
  }
}
