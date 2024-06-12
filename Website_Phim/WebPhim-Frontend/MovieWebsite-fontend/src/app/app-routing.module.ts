
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
import { MovieTypeComponent } from './components/movie-type/movie-type.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { AdminGuardFn } from './guards/admin.guard'; 
import { ListMovieComponent } from './components/admin/movie/list-movie/list-movie.component';
import { UpdateMovieComponent } from './components/admin/movie/update-movie/update-movie.component';
import { AddMovieComponent } from './components/admin/movie/add-movie/add-movie.component';
import { ListGenreComponent } from './components/admin/genre/list-genre/list-genre.component';
import { UpdateGenreComponent } from './components/admin/genre/update-genre/update-genre.component';
import { AddGenreComponent } from './components/admin/genre/add-genre/add-genre.component';
import { ListCountryComponent } from './components/admin/country/list-country/list-country.component';
import { UpdateCountryComponent } from './components/admin/country/update-country/update-country.component';
import { ListMovieTypeComponent } from './components/admin/movie-type/list-movie-type/list-movie-type.component';
import { UpdateMovieTypeComponent } from './components/admin/movie-type/update-movie-type/update-movie-type.component';
import { AddMovieTypeComponent } from './components/admin/movie-type/add-movie-type/add-movie-type.component';
import { ListEpisodeComponent } from './components/admin/episode/list-episode/list-episode.component';
import { UpdateEpisodeComponent } from './components/admin/episode/update-episode/update-episode.component';
import { AddEpisodeComponent } from './components/admin/episode/add-episode/add-episode.component';
import { ListAccountComponent } from './components/admin/account/list-account/list-account.component';
import { ListOrderComponent } from './components/admin/order/list-order/list-order.component';
import { AddCountryComponent } from './components/admin/country/add-country/add-country.component';
import { MovieHotComponent } from './components/movie-hot/movie-hot.component';
import { BookmarkComponent } from './components/bookmark/bookmark.component';
import { AuthenticateAccountComponent } from './components/authenticate-account/authenticate-account.component';
import { PaymentComponent } from './components/payments/payments.component';
import { UpgradeAccountComponent } from './components/upgrade-account/upgrade-account.component';
import { SearchMovieComponent } from './components/search-movie/search-movie.component';


const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', component: HomeComponent },
      { path: 'profile/:id', component: ProfileComponent},
      { path: 'genre/:genre_id', component: GenreComponent },
      { path: 'country/:country_id', component: CountryComponent },
      { path: 'movie_type/:movie_type_id', component: MovieTypeComponent },
      { path: 'detail/:id', component: MovieDetailsComponent},
      { path: 'watching/:id', component: WatchingComponent},
      { path: 'movie-hot', component: MovieHotComponent},
      { path: 'bookmark', component: BookmarkComponent},
      { path: 'search_movie', component: SearchMovieComponent},   
    ]
  },
  {
    path: '',
    component: LoginLayoutComponent,
    children: [
      { path: 'login', component: LoginComponent},
      { path: 'register', component: RegisterComponent},
      { path: 'forgot-password', component: ForgotPasswordComponent},
      { path: 'reset-password', component: ResetPasswordComponent},
      { path: 'authenticate-account', component: AuthenticateAccountComponent},
      { path: 'upgrade-account', component: UpgradeAccountComponent},
      { path: 'payment', component: PaymentComponent},     
    ]
  },
  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: '', component: AdminDashboardComponent , canActivate:[AdminGuardFn]},
      // Movie
      { path: 'movie/list-movie', component: ListMovieComponent, canActivate:[AdminGuardFn]},
      { path: 'movie/update-movie/:id', component: UpdateMovieComponent, canActivate:[AdminGuardFn]},
      { path: 'movie/add-movie', component: AddMovieComponent, canActivate:[AdminGuardFn]},
      //Genre
      { path: 'genre/list-genre', component: ListGenreComponent, canActivate:[AdminGuardFn]},
      { path: 'genre/update-genre/:id', component: UpdateGenreComponent, canActivate:[AdminGuardFn]},
      { path: 'genre/add-genre', component: AddGenreComponent, canActivate:[AdminGuardFn]},
      //Country
      { path: 'country/list-country', component: ListCountryComponent, canActivate:[AdminGuardFn]},
      { path: 'country/update-country/:id', component: UpdateCountryComponent, canActivate:[AdminGuardFn]},
      { path: 'country/add-country', component: AddCountryComponent, canActivate:[AdminGuardFn]},
      // Movie Type
      { path: 'movie-type/list-movie-type', component: ListMovieTypeComponent, canActivate:[AdminGuardFn]},
      { path: 'movie-type/update-movie-type/:id', component: UpdateMovieTypeComponent, canActivate:[AdminGuardFn]},
      { path: 'movie-type/add-movie-type', component: AddMovieTypeComponent, canActivate:[AdminGuardFn]},
      // Episode
      { path: 'episode/list-episode', component: ListEpisodeComponent, canActivate:[AdminGuardFn]},
      { path: 'episode/update-episode/:id', component: UpdateEpisodeComponent, canActivate:[AdminGuardFn]},
      { path: 'episode/add-episode', component: AddEpisodeComponent, canActivate:[AdminGuardFn]},
      // Account
      { path: 'account/list-account', component: ListAccountComponent, canActivate:[AdminGuardFn]},
      // Order
      { path: 'order/list-order', component: ListOrderComponent, canActivate:[AdminGuardFn]},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
