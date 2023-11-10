import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { forkJoin } from 'rxjs';
import { LocalStorageEnum } from 'src/app/enums/local-storage.enum';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { RecordsState } from 'src/app/models/state/records.state';
import { AppStateService } from 'src/app/services/app-state/app-state.service';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { GeographicAreaService } from 'src/app/services/geographic-area/geographic-area.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  form!: FormGroup;

  recordsState: RecordsState = {
    geographicAreas: [],
    customerTypes: [],
    documentTypes: []
  };

  stateIsEmpy: boolean = true;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private messageService: MessageService,
    private appStateService: AppStateService,
    private geographicAreaService: GeographicAreaService,
    private customerService: CustomerService
  ) { }

  ngOnInit(): void {
    localStorage.removeItem(LocalStorageEnum.AuthStateKey);
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.appStateService.isStateEmpty().subscribe((value) => {
      if (value === false) {
        this.stateIsEmpy = false;
      }
    });
  }

  onSubmit(): void {
    this.messageService.clear();

    const formData = this.form.value;
    if (formData.username === "admin" && formData.password === "1234") {
      localStorage.setItem(LocalStorageEnum.AuthStateKey, LocalStorageEnum.SucessAuthStateValue);

      if (this.stateIsEmpy === false) {
        this.router.navigate([Paths.Centers]);
        return;
      }

      forkJoin([
        this.geographicAreaService.getAllAreas(),
        this.customerService.getAllCustomerTypes(),
        this.customerService.getAllDocumentTypes()
      ]).subscribe(([geographicAreas, customerTypes, documentTypes]) => {
        this.recordsState.geographicAreas = geographicAreas;
        this.recordsState.customerTypes = customerTypes;
        this.recordsState.documentTypes = documentTypes;

        this.appStateService.loadRecordsState(this.recordsState);

        this.router.navigate([Paths.Centers]);
      });
    } else {
      this.messageService.add({ severity: 'error', detail: Messages.INCORRECT_CREDETIALS });
    }
  }
}
