import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {User} from "../user/user";
import {Observable} from "rxjs";
import {CustomHttpResponse} from "../user/CustomHttpResponse";

@Injectable({
  providedIn: 'root'
})
export class HttpUserService {

  public url = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getUserTmp(): Observable<User | HttpErrorResponse> {
    return this.http.get<User>('http://localhost:3000/user');
  }

  // urlki sprawdzić
  public getUsers(): Observable<User[] | HttpErrorResponse> {
    return this.http.get<User[]>('${this.url}/user/getAllUsers');
  }

  public getUser(username: string): Observable<User | HttpErrorResponse> {
    return this.http.get<User>('${this.url}/user/getUser/${username}');
  }

  // ogarnąć tego formData, jak to zadziała i czy u mnie zadziała
  public addUser(formData: FormData): Observable<User | HttpErrorResponse> {
    return this.http.post<User>('${this.url}/user/newUser', formData);
  }

  public updateUSer(formData: FormData): Observable<User | HttpErrorResponse> {
    return this.http.post<User>('${this.url}/user/updateUser', formData);
  }

  // zmienić, dostosować do swojego resetu!!!!
  public resetPassword(email: string): Observable<CustomHttpResponse | HttpErrorResponse> {
    return this.http.get<CustomHttpResponse>('${this.url}/user/resetPassword/${email}');
  }

  // urlk-a do poprawy i zmienić w back-endzie info po usunięciu na no-content
  public deleteUser(username: string): Observable<CustomHttpResponse | HttpErrorResponse> {
    return this.http.delete<CustomHttpResponse>('${this.url}/user/removeUser/${username}');
  }

  // użyć formData
  public changePassword(user: User): Observable<User | HttpErrorResponse> {
    return this.http.post<User>('${this.url}/user/changePassword', user);
  }

  public changeRole(user: User): Observable<User | HttpErrorResponse> {
    return this.http.post<User>('${this.url}/user/changeRole', user);
  }

  // typ mówi, że to raczej nie jest najlepszy sposób na operowanie cachem i localStroage, są lepsze
  // i on w innym swoim kursie niby tam to pokazuje, tutaj tylko ta mniej kompleksowa wersja,
  // w innym kursie niby ma lepsze rozwiązania i zamienić te tutaj na tamte,
  // mówił też tutaj o przeciekach, więc koniecznie ogarnąć localStorage z tego drugiego kursu
  public addUsersToLocalCache(users: User[]): void {
    localStorage.setItem('users', JSON.stringify(users));
  }

  public getUsersFromLocalCache(): User[] {
    if (localStorage.getItem('users')) {
      return JSON.parse(localStorage.getItem('users'));
    }
    return null;
  }

  public createUserFormData(loggedInUsername: string, user: User): FormData {
    const formData = new FormData();
    formData.append('currentUsername', loggedInUsername);
    formData.append('name', user.name);
    formData.append('username', user.username);
    formData.append('surname', user.surname);
    formData.append('birthDate', JSON.stringify(user.birthDate));
    formData.append('email', user.email);
    formData.append('phone', user.phone);
    formData.append('address', user.address);
    formData.append('city', user.city);
    formData.append('zipCode', user.zipCode);
    formData.append('country', user.country);
    formData.append('isNotLocked', JSON.stringify(user.isNotLocked));
    formData.append('role', user.role);
    return formData;
  }
}
