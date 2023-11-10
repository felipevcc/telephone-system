import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { Customer } from 'src/app/models/customer-service/customer.interface';
import { DocumentType } from 'src/app/models/customer-service/document-type.interface';
import { AppStateService } from 'src/app/services/app-state/app-state.service';
import { CustomerService } from 'src/app/services/customer/customer.service';

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
  selectedDocType: any;

  customer!: Customer;

  constructor(
    private appStateService: AppStateService,
    private router: Router,
    private fb: FormBuilder,
    private messageService: MessageService,
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
    this.messageService.clear();
    
    const formData = this.form.value;
    this.customerService.getCustomerByDocument(formData.documentTypeId, formData.document).subscribe({
      next: (data) => {
        this.customer = data;
        this.router.navigate([`/${Paths.CustomerDetails}/${this.customer.customerId}`]);
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', detail: Messages.CUSTOMER_NOT_FOUND });
      }
    });
  }

  downloadCustomers(): void {
  }
}
