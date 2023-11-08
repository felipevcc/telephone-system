import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Paths } from './enums/paths.enum';
import { LoginComponent } from './containers/login/login.component';
import { NotFoundComponent } from './containers/not-found/not-found.component';
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
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  {
    path: Paths.Login,
    title: "Acceso - Calitel",
    component: LoginComponent
  },
  {
    path: Paths.Centers,
    title: "Centrales - Calitel",
    component: CentersComponent,
    canActivate: [AuthGuard]
  },
  {
    path: `${Paths.CentersByArea}/:areaId`,
    title: "Centrales encontradas - Calitel",
    component: CentersByAreaComponent,
    canActivate: [AuthGuard]
  },
  {
    path: Paths.RegisterCenter,
    title: "Registrar central - Calitel",
    component: RegisterCenterComponent,
    canActivate: [AuthGuard]
  },
  {
    path: `${Paths.CenterDetails}/:centerId`,
    title: "Detalles de central - Calitel",
    component: CenterDetailsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: `${Paths.UpdateCenter}/:centerId`,
    title: "Actualizar central - Calitel",
    component: UpdateCenterComponent,
    canActivate: [AuthGuard]
  },
  {
    path: Paths.Customers,
    title: "Clientes - Calitel",
    component: CustomersComponent,
    canActivate: [AuthGuard]
  },
  {
    path: Paths.RegisterCustomer,
    title: "Registrar cliente - Calitel",
    component: RegisterCustomerComponent,
    canActivate: [AuthGuard]
  },
  {
    path: Paths.UploadCustomers,
    title: "Cargar clientes - Calitel",
    component: UploadCustomersComponent,
    canActivate: [AuthGuard]
  },
  {
    path: `${Paths.CustomerDetails}/:customerId`,
    title: "Detalles de cliente - Calitel",
    component: CustomerDetailsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: `${Paths.UpdateCustomer}/:customerId`,
    title: "Actualizar cliente - Calitel",
    component: UpdateCustomerComponent,
    canActivate: [AuthGuard]
  },
  {
    path: Paths.TelephoneNumbers,
    title: "Números telefónicos - Calitel",
    component: TelephonesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: `${Paths.TelephoneNumberDetails}/:telephoneNumber`,
    title: "Detalles de número telefónico - Calitel",
    component: TelephoneDetailsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: Paths.TelephoneNumberTrackingProcess,
    title: "Rastreo de números telefónicos - Calitel",
    component: TelephoneTrackingProcessComponent,
    canActivate: [AuthGuard]
  },
  {
    path: Paths.TimeSetting,
    title: "Ajuste de tiempo de liberación - Calitel",
    component: TimeSettingComponent,
    canActivate: [AuthGuard]
  },
  {
    path: Paths.Undefined,
    title: "Página no encontrada - Calitel",
    component: NotFoundComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
