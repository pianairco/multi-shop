import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {PianaStorageService} from "./piana-storage.service";

@Injectable({
  providedIn: 'root'
})
export class ShareStateService {
  private _editModeSubject;
  private _editMode = false;
  private EDIT_MODE_STATE: string = "edit-mode-state";

  constructor(private pianaStorageService: PianaStorageService) {
    let object = pianaStorageService.getObject(this.EDIT_MODE_STATE);
    console.log(object)
    if(object)
      this._editModeSubject = new BehaviorSubject<boolean>(object['editMode']);
    else {
      let editModeObject = { 'editMode': false };
      pianaStorageService.putObject(this.EDIT_MODE_STATE, editModeObject);
      this._editModeSubject = new BehaviorSubject<boolean>(editModeObject['editMode']);
    }
  }

  get editModeSubject () : Observable<boolean> {
    return this._editModeSubject.asObservable();
  }

  set editMode (editMode) {
    this._editMode = editMode;
    this.pianaStorageService.setFieldValue(this.EDIT_MODE_STATE, 'editMode', this._editMode);
    this._editModeSubject.next(this._editMode);
  }

  ifTrue(func) {
    if(this._editMode)
      func.apply();
  }
}
