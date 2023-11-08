import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerTypes } from 'src/app/models/customer-service/customer-types.interface';
import { DocumentTypes } from 'src/app/models/customer-service/document-types.interface';
import { GeographicArea } from 'src/app/models/geographic-area-service/geographic-area.interface';
import { AppStateService } from 'src/app/services/app-state/app-state.service';

@Component({
  selector: 'app-register-center',
  templateUrl: './register-center.component.html',
  styleUrls: ['./register-center.component.scss']
})
export class RegisterCenterComponent implements OnInit {
  geographicAreas$: Observable<GeographicArea[]> = new Observable();
  customerTypes$: Observable<CustomerTypes[]> = new Observable();
  documentTypes$: Observable<DocumentTypes[]> = new Observable();
  constructor(private appStateService: AppStateService) { }

  ngOnInit(): void {
    this.geographicAreas$ = this.appStateService.getGeographicAreas();
    this.customerTypes$ = this.appStateService.getCustomerTypes();
    this.documentTypes$ = this.appStateService.getDocumentTypes();
  }
}
