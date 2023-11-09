import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';

import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { MenubarModule } from 'primeng/menubar';
import { DropdownModule } from 'primeng/dropdown';
import { TableModule } from 'primeng/table';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TopBarComponent } from './components/top-bar/top-bar.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './containers/login/login.component';
import { NotFoundComponent } from './containers/not-found/not-found.component';
import { WelcomeBarComponent } from './components/welcome-bar/welcome-bar.component';
import { CentersComponent } from './containers/centers/centers.component';
import { CentersByAreaComponent } from './containers/centers-by-area/centers-by-area.component';
import { RegisterCenterComponent } from './containers/register-center/register-center.component';
import { CenterDetailsComponent } from './containers/center-details/center-details.component';
import { UpdateCenterComponent } from './containers/update-center/update-center.component';
import { CustomersComponent } from './containers/customers/customers.component';
import { RegisterCustomerComponent } from './containers/register-customer/register-customer.component';
import { UploadCustomersComponent } from './containers/upload-customers/upload-customers.component';
import { CustomerDetailsComponent } from './containers/customer-details/customer-details.component';
import { UpdateCustomerComponent } from './containers/update-customer/update-customer.component';
import { TelephonesComponent } from './containers/telephones/telephones.component';
import { TelephoneDetailsComponent } from './containers/telephone-details/telephone-details.component';
import { TelephoneTrackingProcessComponent } from './containers/telephone-tracking-process/telephone-tracking-process.component';
import { TimeSettingComponent } from './containers/time-setting/time-setting.component';
import { ROOT_REDUCERS } from './state/app.state';


@NgModule({
  declarations: [
    AppComponent,
    TopBarComponent,
    NavbarComponent,
    LoginComponent,
    NotFoundComponent,
    WelcomeBarComponent,
    CentersComponent,
    CentersByAreaComponent,
    RegisterCenterComponent,
    CenterDetailsComponent,
    UpdateCenterComponent,
    CustomersComponent,
    RegisterCustomerComponent,
    UploadCustomersComponent,
    CustomerDetailsComponent,
    UpdateCustomerComponent,
    TelephonesComponent,
    TelephoneDetailsComponent,
    TelephoneTrackingProcessComponent,
    TimeSettingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    StoreModule.forRoot(ROOT_REDUCERS),
    StoreDevtoolsModule.instrument({ name: 'TEST' }),
    FormsModule,
    ReactiveFormsModule,
    MessagesModule,
    ButtonModule,
    InputTextModule,
    BrowserAnimationsModule,
    MenubarModule,
    DropdownModule,
    TableModule
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
