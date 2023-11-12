import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Message } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { Center } from 'src/app/models/center-service/center.interface';
import { AreasPage } from 'src/app/models/geographic-area-service/areas-page.interface';
import { CenterService } from 'src/app/services/center/center.service';
import { GeographicAreaService } from 'src/app/services/geographic-area/geographic-area.service';

@Component({
  selector: 'app-center-details',
  templateUrl: './center-details.component.html',
  styleUrls: ['./center-details.component.scss']
})
export class CenterDetailsComponent implements OnInit {

  centerPath = `/${Paths.Centers}`;
  messages: Message[] = [];
  isLoading: boolean = true;

  selectedCenterId!: number;
  center!: Center;

  areasPage: AreasPage = {
    page: 0,
    pageSize: 5,
    totalRecords: 0,
    totalPages: 0,
    geographicAreas: []
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private centerService: CenterService,
    private geographicAreaService: GeographicAreaService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const selectedCenterIdStr = params.get('centerId');
      if (selectedCenterIdStr != null) {
        this.selectedCenterId = parseInt(selectedCenterIdStr, 10);
        this.isLoading = true;
        this.getCenter(this.selectedCenterId);
      }
    });
  }

  getCenter(selectedCenterId: number): void {
    this.centerService.getCenterById(selectedCenterId).subscribe({
      next: (data) => {
        this.center = data;
        this.getAreasPaged();
      },
      error: (error) => {
        this.isLoading = false;
        console.log(error);
        this.messages = [{ severity: 'error', summary: 'Error', detail: Messages.ERROR_GET }];
      }
    });
  }

  onUpdate(): void {
    this.router.navigate([`/${Paths.UpdateCenter}/${this.center.centerId}`]);
  }

  getAreasPaged(): void {
    this.geographicAreaService.getAreasByCenterPaginator(this.center.centerId, this.areasPage.page, this.areasPage.pageSize).subscribe({
      next: (data) => {
        this.areasPage = data;
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }

  onPageChange(event: any): void {
    this.areasPage.page = event.page;
    this.getAreasPaged();
  }
}
