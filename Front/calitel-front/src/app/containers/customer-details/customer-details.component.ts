import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Message } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { CustomerType } from 'src/app/models/customer-service/customer-type.interface';
import { Customer } from 'src/app/models/customer-service/customer.interface';
import { DocumentType } from 'src/app/models/customer-service/document-type.interface';
import { GeographicArea } from 'src/app/models/geographic-area-service/geographic-area.interface';
import { TelephoneNumber } from 'src/app/models/telephone-number-service/telephone-number.interface';
import { AppStateService } from 'src/app/services/app-state/app-state.service';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { GeographicAreaService } from 'src/app/services/geographic-area/geographic-area.service';
import { TelephoneNumberService } from 'src/app/services/telephone-number/telephone-number.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-customer-details',
  templateUrl: './customer-details.component.html',
  styleUrls: ['./customer-details.component.scss']
})
export class CustomerDetailsComponent implements OnInit {

  customerPath = `/${Paths.Customers}`;
  messages: Message[] = [];
  isLoading: boolean = true;

  selectedCustomerId!: number;

  customer!: Customer;
  documentType!: DocumentType;
  customerType!: CustomerType;
  geographicArea!: GeographicArea;
  telephoneNumber: TelephoneNumber | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private customerService: CustomerService,
    private appStateService: AppStateService,
    private geographicAreaService: GeographicAreaService,
    private telephoneNumberService: TelephoneNumberService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const selectedCustomerIdStr = params.get('customerId');
      if (selectedCustomerIdStr != null) {
        this.selectedCustomerId = parseInt(selectedCustomerIdStr, 10);
        this.isLoading = true;
        this.getCustomer(this.selectedCustomerId);
      }
    });
  }

  getCustomer(selectedCustomerId: number): void {
    this.customerService.getCustomerById(selectedCustomerId).subscribe({
      next: (data) => {
        this.customer = data;
        this.appStateService.getCustomerTypes().subscribe(data => {
          this.customerType = data.find(doc => doc.customerTypeId == this.customer.customerTypeId)!;
        });
        this.appStateService.getDocumentTypes().subscribe(data => {
          this.documentType = data.find(doc => doc.documentTypeId == this.customer.documentTypeId)!;
        });
        this.getGeographicArea();
        this.getTelephoneNumber();
      },
      error: (error) => {
        this.isLoading = false;
        console.log(error);
        this.messages = [{ severity: 'error', summary: 'Error', detail: Messages.ERROR_GET }];
      }
    });
  }

  getGeographicArea(): void {
    this.geographicAreaService.getAreaById(this.customer.areaId).subscribe({
      next: (data) => {
        this.geographicArea = data;
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }

  getTelephoneNumber(): void {
    this.telephoneNumberService.getTelephoneNumberByCustomer(this.customer.customerId).subscribe({
      next: (data) => {
        this.telephoneNumber = data;
      },
      error: (error) => {
        console.log("Customer has no telephone number assigned");
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }

  assignNumber(): void {
    if (this.telephoneNumber) {
      this.messages = [{ severity: 'warn', detail: Messages.INVALID_ACTION }];
      return;
    }
    this.telephoneNumberService.assignTelephoneNumber(this.customer.customerId).subscribe({
      next: (data) => {
        this.telephoneNumber = data;
        this.messages = [{ severity: 'success', detail: Messages.SUCESS_ASSIGN_TELEPHONE }];
      },
      error: (error) => {
        console.log(error.error);
        this.messages = [{ severity: 'error', detail: Messages.ERROR_ASSIGN_TELEPHONE }];
      }
    });
  }

  releaseNumber(): void {
    if (!this.telephoneNumber) {
      this.messages = [{ severity: 'warn', detail: Messages.INVALID_ACTION }];
      return;
    }
    this.telephoneNumberService.releaseTelephoneNumber(this.telephoneNumber!.phoneNumber).subscribe({
      next: (data) => {
        this.telephoneNumber = null;
        this.messages = [{ severity: 'success', detail: Messages.SUCESS_UPDATE }];
      },
      error: (error) => {
        console.log(error);
        this.messages = [{ severity: 'error', detail: Messages.ERROR_UPDATE }];
      }
    });
  }

  onUpdate(): void {
    this.router.navigate([`/${Paths.UpdateCustomer}/${this.customer.customerId}`]);
  }

  downloadHistory(): void {
    this.telephoneNumberService.downloadCustomerHistory(this.customer.customerId).subscribe({
      next: (response: HttpResponse<Blob>) => {
        if (!response.body) {
          console.error("Error downloading customer history");
          this.messages = [{ severity: 'error', detail: Messages.ERROR_GET }];
          return;
        }
        const contentDispositionHeader = response.headers.get('Content-Disposition');
        const filenameMatch = contentDispositionHeader && contentDispositionHeader.match(/filename=(.+)$/);

        const filename = filenameMatch ? filenameMatch[1] : 'customer_history.csv';
        saveAs(response.body, filename);
      },
      error: (error) => {
        console.error("Error downloading customer history:", error);
        this.messages = [{ severity: 'error', detail: Messages.ERROR_GET }];
      }
    });
  }

}
