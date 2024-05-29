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
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProfileComponent } from './components/profile/profile.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';

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
    ProfileComponent,
    ChangePasswordComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    IonicModule.forRoot(),
    CarouselModule,
    FormsModule,
    HttpClientModule,
    NgbModule
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
