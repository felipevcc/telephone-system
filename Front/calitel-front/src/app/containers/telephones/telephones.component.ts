import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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

  form!: FormGroup;

  telephoneNumber!: TelephoneNumber;

  constructor(
    private router: Router,
    private messageService: MessageService,
    private telephoneNumberService: TelephoneNumberService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      telNumber: [null, Validators.required]
    });
  }

  onSearch(): void {
    this.messageService.clear();
    const formData = this.form.value;
    this.telephoneNumberService.getTelephoneNumber(formData.telNumber).subscribe({
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
