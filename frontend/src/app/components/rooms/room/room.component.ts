import {Component, Input, OnInit} from '@angular/core';
import {Room} from '../../../domains/room';
import {Observable} from 'rxjs';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {HttpRoomService} from '../../../services/http/room/http-room.service';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent implements OnInit {

  room: Observable<Room>;

  constructor() {
  }

  ngOnInit(): void {
  }

}
