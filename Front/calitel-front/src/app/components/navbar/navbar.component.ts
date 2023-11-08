import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { Paths } from 'src/app/enums/paths.enum';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  items!: MenuItem[];
  companyName = 'CALITEL';

  constructor(private router: Router) { }

  ngOnInit() {
    this.items = [
      {
        label: "Centrales",
        routerLink: `/${Paths.Centers}`
      },
      {
        label: "Clientes",
        routerLink: `/${Paths.Customers}`
      },
      {
        label: "Números telefónicos",
        items: [
          {
            label: "Consultar número telefónico",
            routerLink: `/${Paths.TelephoneNumbers}`
          },
          {
            label: "Rastrear números liberados",
            routerLink: `/${Paths.TelephoneNumberTrackingProcess}`
          },
          {
            label: "Tiempo requerido de liberación",
            routerLink: `/${Paths.TimeSetting}`
          }
        ]
      }
    ];
  }

  logOut() {
    this.router.navigate([Paths.Login]);
  }
}
