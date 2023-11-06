import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeSettingComponent } from './time-setting.component';

describe('TimeSettingComponent', () => {
  let component: TimeSettingComponent;
  let fixture: ComponentFixture<TimeSettingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TimeSettingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TimeSettingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
