import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { TimeSetting } from 'src/app/models/telephone-number-service/time-setting.interface';
import { TelephoneNumberService } from 'src/app/services/telephone-number/telephone-number.service';

@Component({
  selector: 'app-time-setting',
  templateUrl: './time-setting.component.html',
  styleUrls: ['./time-setting.component.scss']
})
export class TimeSettingComponent implements OnInit {

  lastTimeSetting: TimeSetting = {
    timeId: 0,
    timeValue: -1,
    createdAt: ""
  }

  form!: FormGroup;

  isLoading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private telephoneNumberService: TelephoneNumberService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.getLastTimeSetting();
    this.form = this.fb.group({
      timeValue: [null, Validators.required]
    });
  }

  getLastTimeSetting(): void {
    this.telephoneNumberService.getTimeSetting().subscribe({
      next: (data) => {
        this.lastTimeSetting = data;
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  onSubmit(): void {
    if (this.isLoading) {
      return;
    }
    this.isLoading = true;
    this.messageService.clear();
    const formData = this.form.value;
    this.telephoneNumberService.createTimeSetting(formData.timeValue).subscribe({
      next: (data) => {
        this.lastTimeSetting = data;
        this.messageService.add({ severity: 'success', detail: Messages.SUCESS_CREATE_TIME });
        this.isLoading = false;
      },
      error: (error) => {
        console.log(error);
        this.messageService.add({ severity: 'error', detail: Messages.ERROR_CREATE_TIME });
        this.isLoading = false;
      }
    });
  }
}
