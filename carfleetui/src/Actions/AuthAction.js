import authService from "../Services/AuthService";

export default class AuthActions {

  static async login(data) {
    return authService.login(data).then(res => {
      return res.data || {};
    });
  }

}