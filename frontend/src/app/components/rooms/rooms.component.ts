import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Room} from '../../domains/room';
import {HttpRoomService} from '../../services/http/room/http-room.service';

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent implements OnInit {

  rooms: Observable<Room[]>;

  constructor(private httpRoom: HttpRoomService) {
  }

  ngOnInit(): void {
    this.rooms = this.httpRoom.getRooms();
  }

}
