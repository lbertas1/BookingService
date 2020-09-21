import {Component, OnInit} from '@angular/core';
import {User} from '../../domains/user';
import {Router} from '@angular/router';
import {NgbAlert} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  constructor(private router: Router, public ngbAlert: NgbAlert) {
  }

  user: User;

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user'));

    if (!this.user) {
      // this.router.navigateByUrl('home');
      // this.ngbAlert.ngOnInit();
    }
  }

}
