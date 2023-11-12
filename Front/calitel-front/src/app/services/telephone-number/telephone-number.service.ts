import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TelephoneEndpoints } from 'src/app/enums/telephone-endpoints.enum';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TelephoneNumberService {

  apiUrl: string = `${environment.apiUrl}${environment.apiVersion}`;

  constructor(private http: HttpClient) { }

  getTelephoneNumber(telephoneNumber: number): Observable<any> {
    return this.http.get(`${this.apiUrl}${TelephoneEndpoints.TelephoneDomain}/${telephoneNumber}`);
  }

  getTelephoneNumberByCustomer(customerId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}${TelephoneEndpoints.TelephoneByCustomer}/${customerId}`);
  }

  runTrackingProcess(): Observable<any> {
    return this.http.post(`${this.apiUrl}${TelephoneEndpoints.TelephoneTrackingProcess}`, {}, { responseType: "text" });
  }

  assignTelephoneNumber(customerId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}${TelephoneEndpoints.AssignTelephone}/${customerId}`, {});
  }

  releaseTelephoneNumber(telephoneNumber: number): Observable<any> {
    return this.http.put(`${this.apiUrl}${TelephoneEndpoints.ReleaseTelephone}/${telephoneNumber}`, {});
  }

  downloadCustomerHistory(customerId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}${TelephoneEndpoints.DownloadCustomerHistory}/${customerId}`, { responseType: 'blob', observe: 'response' });
  }

  downloadTelephoneNumberHistory(telephoneNumber: number): Observable<any> {
    return this.http.get(`${this.apiUrl}${TelephoneEndpoints.DownloadTelephoneHistory}/${telephoneNumber}`, { responseType: 'blob', observe: 'response' });
  }

  getTimeSetting(): Observable<any> {
    return this.http.get(`${this.apiUrl}${TelephoneEndpoints.TimeSetting}`);
  }

  createTimeSetting(timeSetting: number): Observable<any> {
    return this.http.post(`${this.apiUrl}${TelephoneEndpoints.TimeSetting}/${timeSetting}`, {});
  }
}
