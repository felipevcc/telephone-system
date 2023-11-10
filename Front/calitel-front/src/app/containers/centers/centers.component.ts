import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Paths } from 'src/app/enums/paths.enum';
import { GeographicArea } from 'src/app/models/geographic-area-service/geographic-area.interface';
import { AppStateService } from 'src/app/services/app-state/app-state.service';

@Component({
  selector: 'app-centers',
  templateUrl: './centers.component.html',
  styleUrls: ['./centers.component.scss']
})
export class CentersComponent implements OnInit {
  registerCenterPath: string = `/${Paths.RegisterCenter}`;

  geographicAreas: GeographicArea[] = [];
  selectedArea: any;

  constructor(private appStateService: AppStateService, private router: Router) { }

  ngOnInit(): void {
    this.appStateService.getGeographicAreas().subscribe(data => {
      this.geographicAreas = data;
    });
  }

  onSearch(): void {
    this.router.navigate([`/${Paths.CentersByArea}/${this.selectedArea.areaId}`]);
  }
}
