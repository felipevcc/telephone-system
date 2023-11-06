import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CentersByAreaComponent } from './centers-by-area.component';

describe('CentersByAreaComponent', () => {
  let component: CentersByAreaComponent;
  let fixture: ComponentFixture<CentersByAreaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CentersByAreaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CentersByAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
