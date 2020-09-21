import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {User} from '../../domains/user';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/auth/authentication.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {HeaderType} from '../../enums/header-type.enum';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  private subscriptions: Subscription[] = [];
  user: User;

  constructor(private router: Router, private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
  }

  public onLogin(user: User): void {
    console.log(user);
    this.subscriptions.push(
      this.authenticationService.login(user).subscribe(
        (response: HttpResponse<User>) => {
          console.log('token from response ' + response.headers.get('Authorization'));
          const token = response.headers.get(HeaderType.AUTHORIZATION);
          this.authenticationService.saveToken(token);
          this.authenticationService.addUserToLocalCache(response.body);
          console.log('token: ' + token);
          // this.router.navigateByUrl('/user/profile/:userId');
          this.user = user;
        },
        (errorResponse: HttpErrorResponse) => {
          console.log(errorResponse.error.error());
        }
      )
    );
  }

  public logout(): void {
    this.authenticationService.logOut();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
