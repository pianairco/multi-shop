import {Injectable} from '@angular/core';
import {
  ActivatedRoute,
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthenticationService} from "../services/authentication-service.service";
import {ShareStateService} from "../services/share-state.service";
import {LogService} from "../services/log.service";

@Injectable({
  providedIn: 'root'
})
export class EditorGuard implements CanActivate {
  constructor(
    private logService: LogService,
    private authenticationService: AuthenticationService,
    private shareStateService: ShareStateService,
    private router: Router,
    private route: ActivatedRoute) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.logService.log("subdomain0", state.root.queryParams, state.root.queryParams['order']);
    // this.shareStateService.editMode = true
    return true;
  }

}
