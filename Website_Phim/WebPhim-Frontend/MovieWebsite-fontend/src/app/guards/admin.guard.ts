import { Injectable, inject } from "@angular/core";
import { TokenService } from "../service/token.service";
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { UserService } from "../service/user.service";
import { UserResponse } from "../responses/user/user.response";

@Injectable({
    providedIn: 'root'
})
export class AdminGuard{
    userResponse?:UserResponse | null;
    constructor(private tokenService: TokenService, private router: Router,
        private userService: UserService
    ){}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean{
        debugger
        const isTokenExpired= this.tokenService.isTokenExpired();
        const isUserIdValid= this.tokenService.getUserId()! >0;
        this.userResponse= this.userService.getUserResponseFromLocalStorage();
        const isAdmin= this.userResponse?.role.name == 'Admin';
        if(!isTokenExpired && isUserIdValid && isAdmin){
            return true;
        }else{
            this.router.navigate(['/login']);
            return false;
        }
    }
}
    export const AdminGuardFn: CanActivateFn=(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot):
        boolean => {
            return inject(AdminGuard).canActivate(next, state);
        }
    
