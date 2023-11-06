import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WelcomeBarComponent } from './welcome-bar.component';

describe('WelcomeBarComponent', () => {
  let component: WelcomeBarComponent;
  let fixture: ComponentFixture<WelcomeBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WelcomeBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WelcomeBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
