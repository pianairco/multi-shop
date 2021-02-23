import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ShareStateService {
  private _editMode: boolean = false;

  constructor() { }

  get editMode () : boolean {
    return this._editMode;
  }

  set editMode (editMode) {
    this._editMode = editMode;
  }
}
