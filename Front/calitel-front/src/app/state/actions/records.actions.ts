import { createAction, props } from "@ngrx/store";
import { GeographicArea } from "../../models/geographic-area-service/geographic-area.interface";
import { CustomerType } from "../../models/customer-service/customer-type.interface";
import { DocumentType } from "../../models/customer-service/document-type.interface";


export const loadGeographicAreas = createAction(
  '[Geographic area list] Load - Login success',
  props<{ geographicAreas: GeographicArea[] }>()
);

export const loadCustomerTypes = createAction(
  '[Customer type list] Load - Login success',
  props<{ customerTypes: CustomerType[] }>()
);

export const loadDocumentTypes = createAction(
  '[Document type list] Load - Login success',
  props<{ documentTypes: DocumentType[] }>()
);
