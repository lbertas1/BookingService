import { TestBed } from '@angular/core/testing';

import { HttpReservationService } from './http-reservation.service';

describe('HttpReservationService', () => {
  let service: HttpReservationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpReservationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
