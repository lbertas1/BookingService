import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RegistrationComponent} from './components/registration/registration.component';
import {ProfileComponent} from './components/profile/profile.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {HomeComponent} from './components/home/home.component';
import {RoomsComponent} from './components/rooms/rooms.component';
import {RoomComponent} from './components/rooms/room/room.component';

const routes: Routes = [
  { path: 'register', component: RegistrationComponent },
  { path: 'user/:userId', component: ProfileComponent },
  { path: 'rooms', component: RoomsComponent },
  { path: 'room/:roomNumber', component: RoomComponent },
  { path: 'home', component: HomeComponent },
  { path: 'page-not-found', component: PageNotFoundComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'page-not-found', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    enableTracing: true,
    onSameUrlNavigation: 'reload'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
