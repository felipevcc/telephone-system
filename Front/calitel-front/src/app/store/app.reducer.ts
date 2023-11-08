import { createReducer, on } from "@ngrx/store";
import { addCustomerTypes, addDocumentTypes, addGeographicAreas } from "./app.actions";
import { AppState } from "./app.state";

const initialState: AppState = {
  geographicAreas: [],
  customerTypes: [],
  documentTypes: []
};

export const appReducer = createReducer(
  initialState,
  on(addGeographicAreas, (state, { geographicAreas }) => ({ ...state, geographicAreas })),
  on(addCustomerTypes, (state, { customerTypes }) => ({ ...state, customerTypes })),
  on(addDocumentTypes, (state, { documentTypes }) => ({ ...state, documentTypes }))
);
