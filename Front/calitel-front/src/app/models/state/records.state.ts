import { CustomerType } from "../customer-service/customer-type.interface";
import { DocumentType } from "../customer-service/document-type.interface";
import { GeographicArea } from "../geographic-area-service/geographic-area.interface";

export interface RecordsState {
  geographicAreas: GeographicArea[];
  customerTypes: CustomerType[];
  documentTypes: DocumentType[];
}
