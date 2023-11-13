import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Message } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { CustomerUpdateReq } from 'src/app/models/customer-service/customer-update-req.interface';
import { Customer } from 'src/app/models/customer-service/customer.interface';
import { CustomerService } from 'src/app/services/customer/customer.service';

@Component({
  selector: 'app-update-customer',
  templateUrl: './update-customer.component.html',
  styleUrls: ['./update-customer.component.scss']
})
export class UpdateCustomerComponent implements OnInit {

  customerPath = `/${Paths.Customers}`;
  form!: FormGroup;
  messages: Message[] = [];
  isLoading: boolean = true;

  customerId!: number;
  customer!: Customer;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private customerService: CustomerService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.maxLength(50), Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.maxLength(20)]],
      address: ['', [Validators.required, Validators.maxLength(100)]]
    });
    this.route.paramMap.subscribe(params => {
      const selectedCustomerIdStr = params.get('customerId');
      if (selectedCustomerIdStr != null) {
        this.customerId = parseInt(selectedCustomerIdStr, 10);
        this.isLoading = true;
        this.getCustomer(this.customerId);
      }
    });
  }

  getCustomer(customerId: number): void {
    this.customerService.getCustomerById(customerId).subscribe({
      next: (data) => {
        this.customer = data;
        // Load data into form
        this.form.patchValue({
          email: this.customer.email,
          phoneNumber: this.customer.phoneNumber,
          address: this.customer.address
        });
        this.isLoading = false;
      },
      error: (error) => {
        this.isLoading = false;
        console.log(error);
        this.messages = [{ severity: 'error', summary: 'Error', detail: Messages.ERROR_GET }];
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    const formData = this.form.value;
    const customer: CustomerUpdateReq = {
      address: formData.address.trim(),
      email: formData.email.trim(),
      phoneNumber: formData.phoneNumber.trim()
    };
    this.customerService.updateCustomer(this.customerId, customer).subscribe({
      next: (response) => {
        this.router.navigate([`/${Paths.CustomerDetails}/${response.customerId}`]);
      },
      error: (error) => {
        console.error('Error updating customer', error);
        this.messages = [{ severity: 'error', detail: Messages.ERROR_UPDATE }];
      },
    });
  }
}
