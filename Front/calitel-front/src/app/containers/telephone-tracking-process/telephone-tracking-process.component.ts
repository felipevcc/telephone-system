import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { TelephoneNumberService } from 'src/app/services/telephone-number/telephone-number.service';

@Component({
  selector: 'app-telephone-tracking-process',
  templateUrl: './telephone-tracking-process.component.html',
  styleUrls: ['./telephone-tracking-process.component.scss']
})
export class TelephoneTrackingProcessComponent implements OnInit {
  constructor(private messageService: MessageService, private telephoneNumberService: TelephoneNumberService) { }

  ngOnInit(): void {
  }

  onExecute(): void {
    this.messageService.clear();
    this.telephoneNumberService.runTrackingProcess().subscribe({
      next: (data) => {
        console.log(data);
        this.messageService.add({ severity: 'success', detail: Messages.EXECUTED_PROCESS });
      },
      error: (error) => {
        console.log(error);
        this.messageService.add({ severity: 'error', detail: Messages.ERROR_EXECUTING_PROCESS });
      }
    });
  }
}
