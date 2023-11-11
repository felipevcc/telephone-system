import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Message } from 'primeng/api';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';
import { CentersPage } from 'src/app/models/center-service/centers-page.interface';
import { GeographicArea } from 'src/app/models/geographic-area-service/geographic-area.interface';
import { CenterService } from 'src/app/services/center/center.service';
import { GeographicAreaService } from 'src/app/services/geographic-area/geographic-area.service';

@Component({
  selector: 'app-centers-by-area',
  templateUrl: './centers-by-area.component.html',
  styleUrls: ['./centers-by-area.component.scss']
})
export class CentersByAreaComponent implements OnInit {

  centerPath = `/${Paths.Centers}`;

  centersPage: CentersPage = {
    page: 0,
    pageSize: 5,
    totalRecords: 0,
    totalPages: 0,
    centers: []
  };

  areaId: number = 0;

  isLoading: boolean = true;

  messages: Message[] = [];

  selectedArea!: GeographicArea;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private centerService: CenterService,
    private areaService: GeographicAreaService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const areaIdStr = params.get('areaId');
      if (areaIdStr != null) {
        this.areaId = parseInt(areaIdStr, 10);

        this.isLoading = true;
        this.getAreaById(this.areaId);
      }
    });
  }

  getAreaById(areaId: number): void {
    this.areaService.getAreaById(areaId).subscribe({
      next: (data) => {
        this.selectedArea = data;
        this.getCentersPaged();
      },
      error: (error) => {
        this.isLoading = false;
        console.log(error);
        this.messages = [{ severity: 'error', summary: 'Error', detail: Messages.ERROR_GET }];
      },
    });
  }

  getCentersPaged(): void {
    this.centerService.getCentersByAreaPaginator(this.areaId, this.centersPage.page, this.centersPage.pageSize).subscribe({
      next: (data) => {
        this.centersPage = data;
        this.isLoading = false;
      },
      error: (error) => {
        this.isLoading = false;
        console.log(error);
      },
    });
  }

  onPageChange(event: any) {
    this.centersPage.page = event.page;
    this.getCentersPaged();
  }

  centerDetail(centerId: number) {
    this.router.navigate([`/${Paths.CenterDetails}/${centerId}`]);
  }
}
