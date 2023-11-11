import { Component, OnInit } from '@angular/core';
import { Message } from 'primeng/api';
import { Paths } from 'src/app/enums/paths.enum';

@Component({
  selector: 'app-center-details',
  templateUrl: './center-details.component.html',
  styleUrls: ['./center-details.component.scss']
})
export class CenterDetailsComponent implements OnInit {

  centerPath = `/${Paths.Centers}`;
  messages: Message[] = [];

  constructor() { }

  ngOnInit(): void {
  }
}
