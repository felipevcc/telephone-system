import { Component } from '@angular/core';

@Component({
  selector: 'app-welcome-bar',
  templateUrl: './welcome-bar.component.html',
  styleUrls: ['./welcome-bar.component.scss']
})
export class WelcomeBarComponent {
  companyName = 'CALITEL';
  welcomeMessage = 'Bienvenido al sistema de asignación de números telefónicos';
}
