import userService from "../Services/UserServices"

export default class UserAction {

  static async registerCustomer(data) {
    return userService.registerCustomer(data).then(res => {

      return res.data || {};
    });
  }
  static async getMangers(data) {
    
    return userService.getManagers(data).then(res => {

      return res.data || {};
    });
  }

  static async getManager(email) {
    return userService.getManager(email).then(res => {
      return res.data || {}
    })
  }

  static async getCustomers(data) {
    return userService.getCustomers(data).then(res => {

      return res.data || {};
    });
  }

  static async addManager(data) {
    return userService.addManager(data).then(res => {
      return res.data || {};
    })
  }

  static async updateManager(id, data) {
    return userService.updateManager(id, data).then(res => {
      return res.data || {};
    })
  }

  static async getCustomer(email) {
    return userService.getCustomer(email).then(res => {
      return res.data || {}
    })
  }

  static async getCustomerById(userId) {
    return userService.getCustomerById(userId).then(res => {
      return res.data || {}
    })
  }

  static async getCustomerRentalHistory(userId) {
    return userService.getCustomeRentalHistory(userId).then(res => {
      return res.data || {}
    })
  }

  

}

export const registerCustomer = UserAction.registerCustomer;
export const getManagers = UserAction.getMangers;
export const getCustomers = UserAction.getCustomers;
export const addManager = UserAction.addManager;
export const getCustomer = UserAction.getCustomer;
export const getCustomerRentalHistory = UserAction.getCustomerRentalHistory;
export const updateManager = UserAction.updateManager;
export const getManager = UserAction.getManager;
export const getCustomerById = UserAction.getCustomerById;