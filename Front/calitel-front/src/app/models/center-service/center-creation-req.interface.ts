// Center creation request interface
export interface CenterCreationReq {
  name: string;
  address: string;
  email: string;
  phoneNumber: string;
  initialNumber: number;
  finalNumber: number;
  geographicAreasIds: number[];
}
