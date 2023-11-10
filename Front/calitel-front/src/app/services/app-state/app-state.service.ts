import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, catchError, map, of } from 'rxjs';
import { CustomerType } from 'src/app/models/customer-service/customer-type.interface';
import { DocumentType } from 'src/app/models/customer-service/document-type.interface';
import { GeographicArea } from 'src/app/models/geographic-area-service/geographic-area.interface';
import { RecordsState } from 'src/app/models/state/records.state';
import { loadCustomerTypes, loadDocumentTypes, loadGeographicAreas } from 'src/app/state/actions/records.actions';
import { selectCustomerTypes, selectDocumentTypes, selectGeographicAreas, selectRecordsFeature } from 'src/app/state/selectors/records.selectors';

@Injectable({
  providedIn: 'root'
})
export class AppStateService {

  constructor(private store: Store<any>) { }

  isStateEmpty(): Observable<boolean> {
    return this.store.select(selectRecordsFeature).pipe(
      map((state) => {
        if (!state) {
          return true;
        }
        return (
          state.geographicAreas.length === 0 ||
          state.customerTypes.length === 0 ||
          state.documentTypes.length === 0
        );
      }),
      catchError((error) => {
        console.log(error);
        return of(true);
      })
    );
  }

  loadRecordsState(recordsState: RecordsState): void {
    this.store.dispatch(loadGeographicAreas({ geographicAreas: recordsState.geographicAreas }));
    this.store.dispatch(loadCustomerTypes({ customerTypes: recordsState.customerTypes }));
    this.store.dispatch(loadDocumentTypes({ documentTypes: recordsState.documentTypes }));
  }

  getGeographicAreas(): Observable<GeographicArea[]> {
    return this.store.select(selectGeographicAreas);
  }

  getCustomerTypes(): Observable<CustomerType[]> {
    return this.store.select(selectCustomerTypes);
  }

  getDocumentTypes(): Observable<DocumentType[]> {
    return this.store.select(selectDocumentTypes);
  }
}
