// Customer creation request interface
export interface CustomerCreationReq {
  customerTypeId: number;
  name: string;
  lastName: string;
  birthdate: string;
  documentTypeId: number;
  document: string;
  address: string;
  areaId: number;
  email: string;
  phoneNumber: string;
}
