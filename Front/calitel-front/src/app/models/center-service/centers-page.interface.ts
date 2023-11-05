import { Center } from "./center.interface";

export interface CentersPage {
  page: number;
  pageSize: number;
  totalRecords: number;
  totalPages: number;
  centers: Center[];
}
