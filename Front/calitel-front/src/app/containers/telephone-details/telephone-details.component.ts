import { Component, OnInit } from '@angular/core';
import { Message } from 'primeng/api';
import { TelephoneNumberService } from 'src/app/services/telephone-number/telephone-number.service';
import { saveAs } from 'file-saver';
import { HttpResponse } from '@angular/common/http';
import { Messages } from 'src/app/enums/messages.enum';
import { TelephoneNumber } from 'src/app/models/telephone-number-service/telephone-number.interface';
import { ActivatedRoute } from '@angular/router';
import { Paths } from 'src/app/enums/paths.enum';
import { Customer } from 'src/app/models/customer-service/customer.interface';
import { Center } from 'src/app/models/center-service/center.interface';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { CenterService } from 'src/app/services/center/center.service';
import { AppStateService } from 'src/app/services/app-state/app-state.service';
import { DocumentType } from 'src/app/models/customer-service/document-type.interface';

@Component({
  selector: 'app-telephone-details',
  templateUrl: './telephone-details.component.html',
  styleUrls: ['./telephone-details.component.scss']
})
export class TelephoneDetailsComponent implements OnInit {

  telephonePath = `/${Paths.TelephoneNumbers}`;
  messages: Message[] = [];
  isLoading: boolean = true;

  selectedNumber!: number;

  telephoneNumber!: TelephoneNumber;
  customer!: Customer;
  documentType!: DocumentType;
  center!: Center;

  constructor(
    private route: ActivatedRoute,
    private telephoneNumberService: TelephoneNumberService,
    private customerService: CustomerService,
    private centerService: CenterService,
    private appStateService: AppStateService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const selectedNumberStr = params.get('telephoneNumber');
      if (selectedNumberStr != null) {
        this.selectedNumber = parseInt(selectedNumberStr, 10);
        this.isLoading = true;
        this.getTelephoneNumber(this.selectedNumber);
      }
    });
  }

  getTelephoneNumber(selectedNumber: number): void {
    this.telephoneNumberService.getTelephoneNumber(selectedNumber).subscribe({
      next: (data) => {
        this.telephoneNumber = data;
        this.getCustomer();
        this.getCenter();
      },
      error: (error) => {
        this.isLoading = false;
        console.log(error);
        this.messages = [{ severity: 'error', summary: 'Error', detail: Messages.ERROR_GET }];
      }
    });
  }

  getCustomer(): void {
    this.customerService.getCustomerById(this.telephoneNumber.customerId).subscribe({
      next: (data) => {
        this.customer = data;
        this.appStateService.getDocumentTypes().subscribe(data => {
          this.documentType = data.find(doc => doc.documentTypeId == this.customer.documentTypeId)!;
        });
        this.isLoading = false;
      },
      error: (error) => {
        this.isLoading = false;
        console.log(error);
      }
    });
  }

  getCenter(): void {
    this.centerService.getCenterById(this.telephoneNumber.centerId).subscribe({
      next: (data) => {
        this.center = data;
        this.isLoading = false;
      },
      error: (error) => {
        this.isLoading = false;
        console.log(error);
      }
    });
  }

  downloadHistory(): void {
    this.telephoneNumberService.downloadTelephoneNumberHistory(this.telephoneNumber.phoneNumber).subscribe({
      next: (response: HttpResponse<Blob>) => {
        if (!response.body) {
          console.error("Error downloading number history");
          this.messages = [{ severity: 'error', detail: Messages.ERROR_GET }];
          return;
        }
        const contentDispositionHeader = response.headers.get('Content-Disposition');
        const filenameMatch = contentDispositionHeader && contentDispositionHeader.match(/filename=(.+)$/);

        const filename = filenameMatch ? filenameMatch[1] : 'number_history.csv';
        saveAs(response.body, filename);
      },
      error: (error) => {
        console.error("Error downloading number history:", error);
        this.messages = [{ severity: 'error', detail: Messages.ERROR_GET }];
      }
    });
  }

}
