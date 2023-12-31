import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalStorageEnum } from '../enums/local-storage.enum';
import { Paths } from '../enums/paths.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {

  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (localStorage.getItem(LocalStorageEnum.AuthStateKey) === LocalStorageEnum.SucessAuthStateValue) {
        return true;
      }
      return this.router.navigate([Paths.Login]).then(() => false);
    }
}
