import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AreaEndpoints } from 'src/app/enums/area-endpoints.enum';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GeographicAreaService {

  apiUrl: string = `${environment.apiUrl}${environment.apiVersion}`;

  constructor(private http: HttpClient) { }

  getAreasByCenterPaginator(centerId: number, page: number, pageSize: number): Observable<any> {
    return this.http.get(`${this.apiUrl}${AreaEndpoints.AreasByCenterPaginator}?centerId=${centerId}&page=${page}&pageSize=${pageSize}`);
  }

  // Get list of areas (fixed size) to store in redux
  getAllAreas(): Observable<any> {
    return this.http.get(`${this.apiUrl}${AreaEndpoints.AreaDomain}`);
  }

  getAreaById(areaId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}${AreaEndpoints.AreaDomain}/${areaId}`);
  }
}
