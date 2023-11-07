import { TestBed } from '@angular/core/testing';

import { TelephoneNumberService } from './telephone-number.service';

describe('TelephoneNumberService', () => {
  let service: TelephoneNumberService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TelephoneNumberService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
