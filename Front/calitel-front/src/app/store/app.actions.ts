import { createAction, props } from "@ngrx/store";
import { GeographicArea } from "../models/geographic-area-service/geographic-area.interface";
import { CustomerTypes } from "../models/customer-service/customer-types.interface";
import { DocumentTypes } from "../models/customer-service/document-types.interface";


export const addGeographicAreas = createAction('[Geographic Area] Add', props<{ geographicAreas: GeographicArea[] }>());
export const addCustomerTypes = createAction('[Customer Types] Add', props<{ customerTypes: CustomerTypes[] }>());
export const addDocumentTypes = createAction('[Document Types] Add', props<{ documentTypes: DocumentTypes[] }>());
