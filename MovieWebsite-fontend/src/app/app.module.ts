import { NgModule, OnInit  } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { IonicModule } from '@ionic/angular';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { GenreComponent } from './components/genre/genre.component';
import { RegisterComponent } from './components/register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './intercrptors/token.interceptor';
import { TopViewsComponent } from './components/top-views/top-views.component';
import { MovieDetailsComponent } from './components/movie-details/movie-details.component';
import { WatchingComponent } from './components/watching/watching.component';
import { MovieRelatedComponent } from './components/movie-related/movie-related.component';
import { CountryComponent } from './components/country/country.component';
import { ProfileComponent } from './components/profile/profile.component';
import { LoginLayoutComponent } from './layouts/login-layout/login-layout.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { RatingComponent } from './components/rating/rating.component';
import { CommentComponent } from './components/comment/comment.component';
import { AdminSidebarComponent } from './components/admin/admin-sidebar/admin-sidebar.component';
import { AdminHeaderComponent } from './components/admin/admin-header/admin-header.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { MovieTypeComponent } from './components/movie-type/movie-type.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ListMovieComponent } from './components/admin/movie/list-movie/list-movie.component';
import { UpdateMovieComponent } from './components/admin/movie/update-movie/update-movie.component';
import { AddMovieComponent } from './components/admin/movie/add-movie/add-movie.component';
import { ListCountryComponent } from './components/admin/country/list-country/list-country.component';
import { UpdateCountryComponent } from './components/admin/country/update-country/update-country.component';
import { AddCountryComponent } from './components/admin/country/add-country/add-country.component';
import { ListGenreComponent } from './components/admin/genre/list-genre/list-genre.component';
import { UpdateGenreComponent } from './components/admin/genre/update-genre/update-genre.component';
import { AddGenreComponent } from './components/admin/genre/add-genre/add-genre.component';
import { ListMovieTypeComponent } from './components/admin/movie-type/list-movie-type/list-movie-type.component';
import { UpdateMovieTypeComponent } from './components/admin/movie-type/update-movie-type/update-movie-type.component';
import { AddMovieTypeComponent } from './components/admin/movie-type/add-movie-type/add-movie-type.component';
import { ListEpisodeComponent } from './components/admin/episode/list-episode/list-episode.component';
import { UpdateEpisodeComponent } from './components/admin/episode/update-episode/update-episode.component';
import { AddEpisodeComponent } from './components/admin/episode/add-episode/add-episode.component';
import { ListAccountComponent } from './components/admin/account/list-account/list-account.component';
import { ListOrderComponent } from './components/admin/order/list-order/list-order.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    GenreComponent,
    RegisterComponent,
    TopViewsComponent,
    MovieDetailsComponent,
    WatchingComponent,
    MovieRelatedComponent,
    CountryComponent,
    ProfileComponent,
    LoginLayoutComponent,
    MainLayoutComponent,
    ChangePasswordComponent,
    RatingComponent,
    CommentComponent,
    AdminSidebarComponent,
    AdminHeaderComponent,
    AdminDashboardComponent,
    AdminLayoutComponent,
    MovieTypeComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    ListMovieComponent,
    UpdateMovieComponent,
    AddMovieComponent,
    ListCountryComponent,
    UpdateCountryComponent,
    AddCountryComponent,
    ListGenreComponent,
    UpdateGenreComponent,
    AddGenreComponent,
    ListMovieTypeComponent,
    UpdateMovieTypeComponent,
    AddMovieTypeComponent,
    ListEpisodeComponent,
    UpdateEpisodeComponent,
    AddEpisodeComponent,
    ListAccountComponent,
    ListOrderComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    IonicModule.forRoot(),
    CarouselModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [
     AppComponent
  ]
})
export class AppModule { 

}
