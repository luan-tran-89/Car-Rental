import defaultRestApi from '../Common/RestAPiInstance';
import APIs from "../Common/API"


export default class UserService {

  static registerCustomer = (data) => {
    return defaultRestApi.post(APIs.register(), data);
  }
  static getManagers = (token) => {
    return defaultRestApi.get(APIs.getManagers(), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
  }

  static getManager = (email) => {
    return defaultRestApi.get(APIs.getManager(email), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
  }
  static getCustomers = (token) => {
    return defaultRestApi.get(APIs.getCustomers(), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
  }

  static addManager = (data) => {
    // console.log(localStorage.getItem('token'))
    return defaultRestApi.post(APIs.addManager(), data, {
      
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
    })
  }

  static updateManager = (id, data) => {
    return defaultRestApi.put(APIs.updateManager(id), data, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
    })
  }

  static getCustomer = (email) => {
    return defaultRestApi.get(APIs.getCustomer(email), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
  }
  static getCustomerById = (userId) => {
    return defaultRestApi.get(APIs.getCustomerById(userId), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
  }

  static getCustomeRentalHistory = (userId) => {
    return defaultRestApi.get(APIs.getCustomerCarRentalHistory(userId), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
  }

  // static getCustomerCarRentalHistory = () => {
  //   return defaultRestApi.get(APIs.getCustomerRentalHisotry(), {
  //     headers: {
  //       'Authorization': `Bearer ${localStorage.getItem('token')}`
  //     }
  //   })
  // },

}