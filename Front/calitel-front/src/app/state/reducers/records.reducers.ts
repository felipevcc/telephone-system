import { createReducer, on } from "@ngrx/store";
import { loadCustomerTypes, loadDocumentTypes, loadGeographicAreas } from "../actions/records.actions";
import { RecordsState } from "../../models/state/records.state";

export const initialState: RecordsState = {
  geographicAreas: [],
  customerTypes: [],
  documentTypes: []
};

export const recordsReducer = createReducer(
  initialState,
  on(loadGeographicAreas, (state, { geographicAreas }) => ({ ...state, geographicAreas })),
  on(loadCustomerTypes, (state, { customerTypes }) => ({ ...state, customerTypes })),
  on(loadDocumentTypes, (state, { documentTypes }) => ({ ...state, documentTypes }))
);
