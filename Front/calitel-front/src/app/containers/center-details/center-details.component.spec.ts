import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CenterDetailsComponent } from './center-details.component';

describe('CenterDetailsComponent', () => {
  let component: CenterDetailsComponent;
  let fixture: ComponentFixture<CenterDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CenterDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CenterDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
