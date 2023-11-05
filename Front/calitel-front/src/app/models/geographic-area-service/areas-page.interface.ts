import { GeographicArea } from "./geographic-area.interface";

export interface AreasPage {
  page: number;
  pageSize: number;
  totalRecords: number;
  totalPages: number;
  geographicAreas: GeographicArea[];
}
