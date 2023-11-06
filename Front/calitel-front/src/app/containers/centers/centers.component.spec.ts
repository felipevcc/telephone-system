import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CentersComponent } from './centers.component';

describe('CentersComponent', () => {
  let component: CentersComponent;
  let fixture: ComponentFixture<CentersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CentersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CentersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
