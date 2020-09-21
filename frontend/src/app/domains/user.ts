import {Role} from '../enums/role.enum';

export class User {
  public name: string;
  public surname: string;
  public birthDate: Date;
  public username: string;
  public email: string;
  public phone: string;
  public address: string;
  public city: string;
  public zipCode: string;
  public country: string;
  public isNotLocked: boolean;
  public role: Role;
  private password: string;

  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }

// konstruktor do ustawiania wartosci domyslnych???


}
