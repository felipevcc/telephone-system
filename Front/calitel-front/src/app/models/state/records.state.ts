import { CustomerTypes } from "../customer-service/customer-types.interface";
import { DocumentTypes } from "../customer-service/document-types.interface";
import { GeographicArea } from "../geographic-area-service/geographic-area.interface";

export interface RecordsState {
  geographicAreas: GeographicArea[];
  customerTypes: CustomerTypes[];
  documentTypes: DocumentTypes[];
}
