import { EntityState } from "@ngrx/entity";
import { GeographicArea } from "../models/geographic-area-service/geographic-area.interface";
import { CustomerTypes } from "../models/customer-service/customer-types.interface";
import { DocumentTypes } from "../models/customer-service/document-types.interface";

export interface AppState {
  geographicAreas: GeographicArea[];
  customerTypes: CustomerTypes[];
  documentTypes: DocumentTypes[];
}
