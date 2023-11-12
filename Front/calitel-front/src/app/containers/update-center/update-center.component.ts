import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Message } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { CenterUpdateReq } from 'src/app/models/center-service/center-update-req.interface';
import { Center } from 'src/app/models/center-service/center.interface';
import { CenterService } from 'src/app/services/center/center.service';

@Component({
  selector: 'app-update-center',
  templateUrl: './update-center.component.html',
  styleUrls: ['./update-center.component.scss']
})
export class UpdateCenterComponent implements OnInit {

  centerPath = `/${Paths.Centers}`;
  form!: FormGroup;
  messages: Message[] = [];
  isLoading: boolean = true;

  centerId!: number;
  center!: Center;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private centerService: CenterService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.maxLength(50), Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.maxLength(20)]],
      address: ['', [Validators.required, Validators.maxLength(100)]]
    });
    this.route.paramMap.subscribe(params => {
      const selectedCustomerIdStr = params.get('centerId');
      if (selectedCustomerIdStr != null) {
        this.centerId = parseInt(selectedCustomerIdStr, 10);
        this.isLoading = true;
        this.getCenter(this.centerId);
      }
    });
  }

  getCenter(centerId: number): void {
    this.centerService.getCenterById(centerId).subscribe({
      next: (data) => {
        this.center = data;
        // Load data into form
        this.form.patchValue({
          address: this.center.address,
          email: this.center.email,
          phoneNumber: this.center.phoneNumber
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
    const center: CenterUpdateReq = {
      email: formData.email,
      phoneNumber: formData.phoneNumber,
      address: formData.address
    };
    this.centerService.updateCenter(this.centerId, center).subscribe({
      next: (response) => {
        this.router.navigate([`/${Paths.CenterDetails}/${response.centerId}`]);
      },
      error: (error) => {
        console.error('Error updating center', error);
        this.messages = [{ severity: 'error', detail: Messages.ERROR_UPDATE }];
      },
    });
  }
}
