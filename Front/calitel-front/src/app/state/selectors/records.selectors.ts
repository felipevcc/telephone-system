import { createSelector } from '@ngrx/store';
import { AppState } from '../app.state';
import { RecordsState } from 'src/app/models/state/records.state';

export const selectRecordsFeature = (state: AppState) => state.records;

export const selectGeographicAreas = createSelector(
  selectRecordsFeature,
  (state: RecordsState) => state.geographicAreas
);

export const selectCustomerTypes = createSelector(
  selectRecordsFeature,
  (state: RecordsState) => state.customerTypes
);

export const selectDocumentTypes = createSelector(
  selectRecordsFeature,
  (state: RecordsState) => state.documentTypes
);
