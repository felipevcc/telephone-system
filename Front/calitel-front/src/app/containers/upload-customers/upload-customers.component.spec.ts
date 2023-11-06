import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadCustomersComponent } from './upload-customers.component';

describe('UploadCustomersComponent', () => {
  let component: UploadCustomersComponent;
  let fixture: ComponentFixture<UploadCustomersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadCustomersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadCustomersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
