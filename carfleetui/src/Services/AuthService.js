import defaultRestApi from '../Common/RestAPiInstance';
import APIs from '../Common/API';


export default class AuthService {

  static login = (data) => {
    return defaultRestApi.post(APIs.getToken(), data);
  }

  static registerCustomer = (data) => {
    return defaultRestApi.post(APIs.register(), data);
  }

}