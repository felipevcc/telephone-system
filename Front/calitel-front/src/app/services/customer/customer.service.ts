import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerEndpoints } from 'src/app/enums/customer-endpoints.enum';
import { CustomerCreationReq } from 'src/app/models/customer-service/customer-creation-req.interface';
import { CustomerUpdateReq } from 'src/app/models/customer-service/customer-update-req.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  apiUrl: string = `${environment.apiUrl}${environment.apiVersion}`;

  constructor(private http: HttpClient) { }

  // Get list of customer types (fixed size) to store in redux
  getAllCustomerTypes(): Observable<any> {
    return this.http.get(`${this.apiUrl}${CustomerEndpoints.CustomerTypes}`);
  }

  // Get list of document types (fixed size) to store in redux
  getAllDocumentTypes(): Observable<any> {
    return this.http.get(`${this.apiUrl}${CustomerEndpoints.DocumentTypes}`);
  }

  getCustomerByDocument(documentTypeId: number, document: string): Observable<any> {
    return this.http.get(`${this.apiUrl}${CustomerEndpoints.CustomerDomain}/${documentTypeId}/${document}`);
  }

  getCustomerById(customerId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}${CustomerEndpoints.CustomerDomain}/${customerId}`);
  }

  createCustomer(customerData: CustomerCreationReq): Observable<any> {
    return this.http.post(`${this.apiUrl}${CustomerEndpoints.CustomerDomain}`, customerData);
  }

  updateCustomer(customerId: number, customerData: CustomerUpdateReq): Observable<any> {
    return this.http.put(`${this.apiUrl}${CustomerEndpoints.CustomerDomain}/${customerId}`, customerData);
  }

  downloadCustomers(): Observable<any> {
    return this.http.get(`${this.apiUrl}${CustomerEndpoints.DownloadCustomers}`, { responseType: 'blob' });
  }

  uploadCustomers(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}${CustomerEndpoints.UploadCustomers}`, formData, { responseType: 'blob' });
  }
}
