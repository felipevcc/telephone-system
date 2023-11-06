import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelephoneTrackingProcessComponent } from './telephone-tracking-process.component';

describe('TelephoneTrackingProcessComponent', () => {
  let component: TelephoneTrackingProcessComponent;
  let fixture: ComponentFixture<TelephoneTrackingProcessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TelephoneTrackingProcessComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TelephoneTrackingProcessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
