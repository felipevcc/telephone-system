import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelephonesComponent } from './telephones.component';

describe('TelephonesComponent', () => {
  let component: TelephonesComponent;
  let fixture: ComponentFixture<TelephonesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TelephonesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TelephonesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
