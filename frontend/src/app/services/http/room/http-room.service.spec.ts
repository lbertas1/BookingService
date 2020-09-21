import { TestBed } from '@angular/core/testing';

import { HttpRoomService } from './http-room.service';

describe('HttpRoomService', () => {
  let service: HttpRoomService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpRoomService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
