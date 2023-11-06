import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BarComponent } from './components/bar/bar.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './containers/login/login.component';
import { NotFoundComponent } from './containers/not-found/not-found.component';
import { WelcomeBarComponent } from './components/welcome-bar/welcome-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    BarComponent,
    NavbarComponent,
    LoginComponent,
    NotFoundComponent,
    WelcomeBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
