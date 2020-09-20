import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {User} from "../user/user";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url: string = environment.apiBaseUrl;
  private token: string;
  private loggedInUsername: string;
  private jwt = new JwtHelperService();

  constructor(private http: HttpClient) {
  }

  // public login(user: User): Observable<HttpResponse<User>> {
  //   return this.http.post<User>('${this.url}/user/login', user, {observe: 'response'});
  // }

  public login(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>('http://localhost:3000/user', user, {observe: 'response'});
  }

  public register(user: User): Observable<User> {
    return this.http.post<User>('${this.url}/user/register', user);
  }

  public logout(): void {
    this.token = null;
    this.loggedInUsername = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
  }

  public saveToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', this.token);
  }

  public addUserToLocalCache(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUserFromLocalCache(): User {
    return JSON.parse(localStorage.getItem('user'));
  }

  public loadToken(): void {
    this.token = localStorage.getItem('token');
  }

  public getToken(): string {
    return this.token;
  }

  // poprawić wygląd potem
  public isLoggedIn(): boolean {
    this.loadToken();
    if (this.token != null && this.token !== '') {
      if (this.jwt.decodeToken(this.token).sub != null || '') {
        if (this.jwt.isTokenExpired(this.token)) {
          this.loggedInUsername = this.jwt.decodeToken(this.token).sub;
          return true;
        }
      }
    } else {
      this.logout();
      return false;
    }
  }
}
