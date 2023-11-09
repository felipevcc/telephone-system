import { Component, OnInit } from '@angular/core';
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
  registerCenterPath = `/${Paths.RegisterCenter}`;

  geographicAreas: GeographicArea[] = [];
  selectedArea: any;

  constructor(private appStateService: AppStateService) { }

  ngOnInit(): void {
    this.appStateService.getGeographicAreas().subscribe(data => {
      this.geographicAreas = data;
    });
  }

  onSearch(): void {
    console.log(this.selectedArea);
  }
}
