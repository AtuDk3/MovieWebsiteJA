import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { MovieDetailsComponent } from './components/movie-details/movie-details.component';
import { RegisterComponent } from './components/register/register.component';
import { WatchingComponent } from './components/watching/watching.component';
import { GenreComponent } from './components/genre/genre.component';
import { CountryComponent } from './components/country/country.component';
import { ProfileComponent } from './components/profile/profile.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { LoginLayoutComponent } from './layouts/login-layout/login-layout.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { TheatersMovieComponent } from './components/theaters-movie/theaters-movie.component';

// const routes: Routes = [
//   { path: '', component: HomeComponent},
//   { path: 'login', component: LoginComponent},
//    { path: 'register', component: RegisterComponent},
//    { path: 'profile', component: ProfileComponent},
//    { path: 'genre/:genre_id', component: GenreComponent },
//    { path: 'country/:country_id', component: CountryComponent },
//    { path: 'detail/:id', component: MovieDetailsComponent},
//    { path: 'watching/:id', component: WatchingComponent},
// ];

const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', component: HomeComponent },
      { path: 'profile', component: ProfileComponent},
      { path: 'genre/:genre_id', component: GenreComponent },
      { path: 'country/:country_id', component: CountryComponent },
      { path: 'movie_type/:movie_type_id', component: TheatersMovieComponent },
      { path: 'detail/:id', component: MovieDetailsComponent},
      { path: 'watching/:id', component: WatchingComponent},
    ]
  },
  {
    path: '',
    component: LoginLayoutComponent,
    children: [
      { path: 'login', component: LoginComponent},
      { path: 'register', component: RegisterComponent}
    ]
  },
  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: '', component: AdminDashboardComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

