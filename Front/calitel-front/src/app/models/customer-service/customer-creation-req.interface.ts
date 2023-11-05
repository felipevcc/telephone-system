// Customer creation request interface
export interface CustomerCreationReq {
  customerTypeId: number;
  name: string;
  lastName: string;
  birthDate: string;
  documentTypeId: number;
  document: string;
  address: string;
  areaId: number;
  email: string;
  phoneNumber: string;
}
