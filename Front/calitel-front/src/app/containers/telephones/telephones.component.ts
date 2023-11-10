import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { TelephoneNumber } from 'src/app/models/telephone-number-service/telephone-number.interface';
import { TelephoneNumberService } from 'src/app/services/telephone-number/telephone-number.service';

@Component({
  selector: 'app-telephones',
  templateUrl: './telephones.component.html',
  styleUrls: ['./telephones.component.scss']
})
export class TelephonesComponent implements OnInit {

  telephoneNumber!: TelephoneNumber;

  selectedNumber!: number;

  constructor(
    private router: Router,
    private messageService: MessageService,
    private telephoneNumberService: TelephoneNumberService
  ) { }

  ngOnInit(): void {
  }

  onSearch(): void {
    this.messageService.clear();
    this.telephoneNumberService.getTelephoneNumber(this.selectedNumber).subscribe({
      next: (data) => {
        this.telephoneNumber = data;
        this.router.navigate([`/${Paths.TelephoneNumberDetails}/${this.telephoneNumber.phoneNumber}`]);
      },
      error: (error) => {
        this.messageService.add({ severity: 'warn', detail: Messages.NUMBER_NOT_FOUND });
      }
    });
  }
}
