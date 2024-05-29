import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { MovieDetailsComponent } from './components/movie-details/movie-details.component';
import { RegisterComponent } from './components/register/register.component';
import { WatchingComponent } from './components/watching/watching.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent},
   { path: 'register', component: RegisterComponent},
   { path: 'detail', component: MovieDetailsComponent},
   { path: 'watching', component: WatchingComponent},
   { path: 'profile/:id', component: ProfileComponent},
   { path: 'change-password', component: ChangePasswordComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

