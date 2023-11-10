import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Message } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { Customer } from 'src/app/models/customer-service/customer.interface';
import { DocumentType } from 'src/app/models/customer-service/document-type.interface';
import { AppStateService } from 'src/app/services/app-state/app-state.service';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { saveAs } from 'file-saver';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.scss']
})
export class CustomersComponent implements OnInit {

  registerCustomerPath: string = `/${Paths.RegisterCustomer}`;
  uploadCustomersPath: string = `/${Paths.UploadCustomers}`;

  documentTypes: DocumentType[] = [];
  form!: FormGroup;

  customer!: Customer;

  onSearchMessages: Message[] = [];
  onDownloadMessages: Message[] = [];

  constructor(
    private appStateService: AppStateService,
    private router: Router,
    private fb: FormBuilder,
    private customerService: CustomerService
  ) { }

  ngOnInit(): void {
    this.appStateService.getDocumentTypes().subscribe(data => {
      this.documentTypes = data;
    });

    this.form = this.fb.group({
      documentType: ['', Validators.required],
      document: ['', Validators.required]
    });
  }

  onSearch(): void {
    const formData = this.form.value;
    this.customerService.getCustomerByDocument(formData.documentType.documentTypeId, formData.document).subscribe({
      next: (data) => {
        this.customer = data;
        this.router.navigate([`/${Paths.CustomerDetails}/${this.customer.customerId}`]);
      },
      error: (error) => {
        this.onSearchMessages = [{ severity: 'error', detail: Messages.CUSTOMER_NOT_FOUND }];
      }
    });
  }

  downloadCustomers(): void {
    this.customerService.downloadCustomers().subscribe({
      next: (response: HttpResponse<Blob>) => {
        if (!response.body) {
          console.error("Error downloading customers");
          this.onDownloadMessages = [{ severity: 'error', detail: Messages.ERROR_GET }];
          return;
        }
        const contentDispositionHeader = response.headers.get('Content-Disposition');
        const filenameMatch = contentDispositionHeader && contentDispositionHeader.match(/filename=(.+)$/);

        const filename = filenameMatch ? filenameMatch[1] : 'customers.csv';
        saveAs(response.body, filename);
      },
      error: (error) => {
        console.error("Error downloading customers:", error);
        this.onDownloadMessages = [{ severity: 'error', detail: Messages.ERROR_GET }];
      }
    });
  }
}
