import {User} from "../user/user";

export interface Session {
  token: string;
  user: User;
  message?: string;
}
