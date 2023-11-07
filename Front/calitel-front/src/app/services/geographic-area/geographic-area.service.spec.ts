import { TestBed } from '@angular/core/testing';

import { GeographicAreaService } from './geographic-area.service';

describe('GeographicAreaService', () => {
  let service: GeographicAreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GeographicAreaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
