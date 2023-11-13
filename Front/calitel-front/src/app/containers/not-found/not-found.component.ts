import { Component, OnInit } from '@angular/core';
import { Paths } from 'src/app/enums/paths.enum';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.scss']
})
export class NotFoundComponent implements OnInit {

  title = 'OOPS!';
  message = 'Error 404 - Page Not Found';
  backPath = `/${Paths.Centers}`;

  constructor() { }

  ngOnInit(): void {
  }
}
