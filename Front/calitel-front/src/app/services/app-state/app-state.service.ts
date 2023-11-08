import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { CustomerTypes } from 'src/app/models/customer-service/customer-types.interface';
import { DocumentTypes } from 'src/app/models/customer-service/document-types.interface';
import { GeographicArea } from 'src/app/models/geographic-area-service/geographic-area.interface';
import { addCustomerTypes, addDocumentTypes, addGeographicAreas } from 'src/app/store/app.actions';
import { AppState } from 'src/app/store/app.state';

@Injectable({
  providedIn: 'root'
})
export class AppStateService {

  constructor(private store: Store<AppState>) { }

  addAppState(appState: AppState): void {
    this.store.dispatch(addGeographicAreas({ geographicAreas: appState.geographicAreas }));
    this.store.dispatch(addCustomerTypes({ customerTypes: appState.customerTypes }));
    this.store.dispatch(addDocumentTypes({ documentTypes: appState.documentTypes }));
  }

  getGeographicAreas(): Observable<GeographicArea[]> {
    return this.store.select(state => state.geographicAreas);
  }

  getCustomerTypes(): Observable<CustomerTypes[]> {
    return this.store.select(state => state.customerTypes);
  }

  getDocumentTypes(): Observable<DocumentTypes[]> {
    return this.store.select(state => state.documentTypes);
  }
}
