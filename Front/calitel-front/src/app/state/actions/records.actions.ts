import { createAction, props } from "@ngrx/store";
import { GeographicArea } from "../../models/geographic-area-service/geographic-area.interface";
import { CustomerTypes } from "../../models/customer-service/customer-types.interface";
import { DocumentTypes } from "../../models/customer-service/document-types.interface";


export const loadGeographicAreas = createAction(
  '[Geographic area list] Load - Login success',
  props<{ geographicAreas: GeographicArea[] }>()
);

export const loadCustomerTypes = createAction(
  '[Customer type list] Load - Login success',
  props<{ customerTypes: CustomerTypes[] }>()
);

export const loadDocumentTypes = createAction(
  '[Document type list] Load - Login success',
  props<{ documentTypes: DocumentTypes[] }>()
);
