import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Message } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { CenterCreationReq } from 'src/app/models/center-service/center-creation-req.interface';
import { GeographicArea } from 'src/app/models/geographic-area-service/geographic-area.interface';
import { AppStateService } from 'src/app/services/app-state/app-state.service';
import { CenterService } from 'src/app/services/center/center.service';

@Component({
  selector: 'app-register-center',
  templateUrl: './register-center.component.html',
  styleUrls: ['./register-center.component.scss']
})
export class RegisterCenterComponent implements OnInit {

  form!: FormGroup;
  messages: Message[] = [];

  geographicAreas: GeographicArea[] = [];

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private appStateService: AppStateService,
    private centerService: CenterService
  ) { }

  ngOnInit(): void {
    this.appStateService.getGeographicAreas().subscribe(data => {
      this.geographicAreas = data;
    });
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(20), Validators.pattern(/^[0-9a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\s]+$/)]],
      address: ['', [Validators.required, Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.maxLength(50), Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.maxLength(20)]],
      initialNumber: [null, [Validators.required, Validators.min(1000000), Validators.max(99999999)]],
      finalNumber: [null, [Validators.required, Validators.min(1000001), Validators.max(99999999), this.validateFinalNumber]],
      areas: [null, [Validators.required]]
    });
  }

  validateFinalNumber = (control: any) => {
    const finalNumber = control.value;
    if (!finalNumber) {
      return null;
    }
    const initialNumber = this.form.get('initialNumber')?.value;
    if (!initialNumber) {
      return null;
    }
    return finalNumber - initialNumber + 1 >= 10000000 ? null : { invalidRange: true };
  }

  onSubmit() {
    if (this.form.invalid) {
      return;
    }
    const formData = this.form.value;
    const center: CenterCreationReq = {
      name: formData.name,
      address: formData.address,
      email: formData.email,
      phoneNumber: formData.phoneNumber,
      initialNumber: formData.initialNumber,
      finalNumber: formData.finalNumber,
      geographicAreasIds: formData.areas.map((area: GeographicArea) => area.areaId)
    };
    this.centerService.createCenter(center).subscribe({
      next: (response) => {
        this.router.navigate([`/${Paths.CenterDetails}/${response.centerId}`]);
      },
      error: (error) => {
        console.error('Error creating center:', error);
        this.messages = [{ severity: 'error', detail: Messages.ERROR_SAVE }];
      }
    });
  }
}
