import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Room} from '../../../domains/room';
import {Observable} from 'rxjs';
import {CustomHttpResponse} from '../../../domains/CustomHttpResponse';
import {IdPeriod} from '../../../domains/IdPeriod';

@Injectable({
  providedIn: 'root'
})
export class HttpRoomService {

  public url = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  // public getRooms(): Observable<Room[] | HttpErrorResponse> {
  //   return this.http.get<Room[]>('${this.url}/rooms');
  // }

  public getRooms(): Observable<Room[]> {
    return this.http.get<Room[]>('http://localhost:3000/room');
  }

  // użyć formData???
  public addRoom(room: Room): Observable<Room | HttpErrorResponse> {
    return this.http.post<Room>('${this.url}/rooms/addRoom', room);
  }

  public getRoomById(roomId: number): Observable<Room | HttpErrorResponse> {
    return this.http.get<Room>('${this.url}/rooms/${roomId}');
  }

  public updateRoom(room: Room): Observable<Room | HttpErrorResponse> {
    return this.http.put<Room>('${this.url}/rooms/updateRoom', room);
  }

  public removeRoom(roomId: number): Observable<CustomHttpResponse | HttpErrorResponse> {
    return this.http.delete<CustomHttpResponse>('${this.url}/rooms//removeRoom/${roomId}');
  }

  public getAllBusyRooms(): Observable<Room[] | HttpErrorResponse> {
    return this.http.get<Room[]>('${this.url}/rooms/busyRooms');
  }

  public getAllEmptyRooms(): Observable<Room[] | HttpErrorResponse> {
    return this.http.get<Room[]>('${this.url}/rooms/emptyRooms');
  }

  public getAllEmptyRoomsInPeriod(idPeriod: IdPeriod): Observable<IdPeriod | HttpErrorResponse> {
    return this.http.post<IdPeriod>('${this.url}/rooms/emptyRoomsInPeriod', idPeriod);
  }

  public getAllRoomsForGivenCapacity(capacity: number): Observable<number | HttpErrorResponse> {
    return this.http.post<number>('${this.url}/rooms/capacity/${capacity}', capacity);
  }

  public isRoomAvailableInGivenPeriod(idPeriod: IdPeriod): Observable<IdPeriod | HttpErrorResponse> {
    return this.http.post<IdPeriod>('${this.url}/rooms/isAvailable', idPeriod);
  }
}
