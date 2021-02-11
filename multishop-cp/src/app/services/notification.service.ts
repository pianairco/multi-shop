import { Injectable } from '@angular/core';
import {BehaviorSubject, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  public editDataDetails: any = {};
  public subject = new Subject<any>();
  private messageSource = new  BehaviorSubject(this.editDataDetails);

  currentMessage = this.messageSource.asObservable();

  constructor() { }

  changeMessage(type: string, message: string) {
    this.messageSource.next(new NotificationModel(type, message));
  }
}

export class NotificationModel {
  type: string;
  message: string;

  constructor(type: string, message: string) {
    this.type = type;
    this.message = message;
  }
}
