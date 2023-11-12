import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Message } from 'primeng/api';
import { forkJoin } from 'rxjs';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { CustomerCreationReq } from 'src/app/models/customer-service/customer-creation-req.interface';
import { CustomerType } from 'src/app/models/customer-service/customer-type.interface';
import { DocumentType } from 'src/app/models/customer-service/document-type.interface';
import { GeographicArea } from 'src/app/models/geographic-area-service/geographic-area.interface';
import { AppStateService } from 'src/app/services/app-state/app-state.service';
import { CustomerService } from 'src/app/services/customer/customer.service';

@Component({
  selector: 'app-register-customer',
  templateUrl: './register-customer.component.html',
  styleUrls: ['./register-customer.component.scss']
})
export class RegisterCustomerComponent implements OnInit {

  form!: FormGroup;
  isLoading: boolean = true;
  messages: Message[] = [];

  documentTypes: DocumentType[] = [];
  customerTypes: CustomerType[] = [];
  geographicAreas: GeographicArea[] = [];

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private appStateService: AppStateService,
    private customerService: CustomerService
  ) { }

  ngOnInit(): void {
    this.appStateService.getDocumentTypes().subscribe(data => {
      this.documentTypes = data;
    });
    this.appStateService.getCustomerTypes().subscribe(data => {
      this.customerTypes = data;
    });
    this.appStateService.getGeographicAreas().subscribe(data => {
      this.geographicAreas = data;
    });
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(25), Validators.pattern(/^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\s]+$/)]],
      lastName: ['', [Validators.required, Validators.maxLength(25), Validators.pattern(/^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\s]+$/)]],
      documentType: [null, [Validators.required]],
      document: ['', [Validators.required, Validators.maxLength(20), Validators.pattern(/^[a-zA-Z0-9]+$/)]],
      birthdate: [null, [Validators.required, this.validateBirthDate]],
      customerType: [null, [Validators.required]],
      email: ['', [Validators.required, Validators.maxLength(50), Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.maxLength(20)]],
      area: [null, [Validators.required]],
      address: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  validateBirthDate(control: any) {
    const birthDate = control.value;
    if (!birthDate) {
      return null;
    }
    const currentYear = new Date();
    const diff = currentYear.getFullYear() - birthDate.getFullYear();
    return diff > 10 ? null : { invalidAge: true };
  }

  onSubmit(): void {
    const formData = this.form.value;
    const customer: CustomerCreationReq = {
      customerTypeId: formData.customerType.customerTypeId,
      name: formData.name,
      lastName: formData.lastName,
      birthdate: formData.birthdate.toISOString().split('T')[0],
      documentTypeId: formData.documentType.documentTypeId,
      document: formData.document,
      address: formData.address,
      areaId: formData.area.areaId,
      email: formData.email,
      phoneNumber: formData.phoneNumber
    };
    this.customerService.createCustomer(customer).subscribe({
      next: (response) => {
        this.router.navigate([`/${Paths.CustomerDetails}/${response.customerId}`]);
      },
      error: (error) => {
        console.error('Error creating customer:', error);
        this.messages = [{ severity: 'error', detail: Messages.ERROR_SAVE }];
      },
    });
  }
}
