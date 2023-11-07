import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CenterEndpoints } from 'src/app/enums/center-endpoints.enum';
import { CenterCreationReq } from 'src/app/models/center-service/center-creation-req.interface';
import { CenterUpdateReq } from 'src/app/models/center-service/center-update-req.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CenterService {

  apiUrl: string = `${environment.apiUrl}${environment.apiVersion}`;

  constructor(private http: HttpClient) { }

  getCentersByAreaPaginator(areaId: number, page: number, pageSize: number): Observable<any> {
    return this.http.get(`${this.apiUrl}${CenterEndpoints.CentersByAreaPaginator}?areaId=${areaId}&page=${page}&pageSize=${pageSize}`);
  }

  getCenterById(centerId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}${CenterEndpoints.CenterDomain}/${centerId}`);
  }

  createCenter(centerData: CenterCreationReq): Observable<any> {
    return this.http.post(`${this.apiUrl}${CenterEndpoints.CenterDomain}`, centerData);
  }

  updateCenter(centerId: number, centerData: CenterUpdateReq): Observable<any> {
    return this.http.put(`${this.apiUrl}${CenterEndpoints.CenterDomain}/${centerId}`, centerData);
  }
}
