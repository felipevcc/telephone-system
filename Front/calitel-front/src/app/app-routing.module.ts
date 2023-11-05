import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Paths } from './enums/paths.enum';
import { LoginComponent } from './containers/login/login.component';
import { NotFoundComponent } from './containers/not-found/not-found.component';

const routes: Routes = [
  {
    path: Paths.Login,
    title: "Login",
    component: LoginComponent
  },
  {
    path: Paths.Undefined,
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
