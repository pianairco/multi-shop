import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PianaStorageService {

  constructor() { }

  putObject(key: string, obj: object){
    localStorage.setItem(key, JSON.stringify(obj));
  }

  getObject(key: string): object {
    return JSON.parse(localStorage.getItem(key));
  }

  getFieldValue(key: string, field: string) {
    return JSON.parse(localStorage.getItem(key))[field];
  }
}
