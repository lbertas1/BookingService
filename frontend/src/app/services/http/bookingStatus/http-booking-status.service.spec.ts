import { TestBed } from '@angular/core/testing';

import { HttpBookingStatusService } from './http-booking-status.service';

describe('HttpBookingStatusService', () => {
  let service: HttpBookingStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpBookingStatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
