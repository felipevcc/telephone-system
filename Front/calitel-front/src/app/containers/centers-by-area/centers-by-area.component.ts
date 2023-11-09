import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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

  centersPage!: CentersPage;

  areaId!: number;
  selectedArea!: GeographicArea;

  page: number = 0;
  pageSize: number = 1;

  first = 0;

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

        this.getAreaById(this.areaId);
        this.getCentersPaged();
      }
    });
  }

  getAreaById(areaId: number): void {
    this.areaService.getAreaById(areaId).subscribe({
      next: (data) => {
        this.selectedArea = data;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  getCentersPaged(): void {
    this.centerService.getCentersByAreaPaginator(this.areaId, this.page, this.pageSize).subscribe({
      next: (data) => {
        this.centersPage = data;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  onPageChange(event: any) {
    this.page = event.page;
    //this.pageSize = event.rows;
    this.getCentersPaged();
  }

  centerDetail(centerId: number) {
    this.router.navigate([`/${Paths.CenterDetails}/${centerId}`]);
  }




  next() {
    this.first = this.first + this.pageSize;
    this.page = this.page + 1;
  }

  prev() {
      this.first = this.first - this.pageSize;
      this.page = this.page - 1;
  }

  reset() {
      this.first = 0;
  }

  isLastPage(): boolean {
      return this.centersPage.centers ? this.first === this.centersPage.totalRecords - this.pageSize : true;
  }

  isFirstPage(): boolean {
      return this.centersPage.centers ? this.first === 0 : true;
  }
}
