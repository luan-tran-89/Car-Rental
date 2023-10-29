import carService from "../Services/CarService"

export default class CarActions {

  static async getCars() {
    return carService.getCars().then(res => {
      return res.data || {}
    })
  }

  static async getCarMaintenanceHistory(id) {
    return carService.getCarMaintenanceHistory(id).then(res => {
      return res.data || {}
    })
  }

  static async addCar(data) {
    return carService.addCar(data).then(res => {
      return res.data || {}
    })
  }

  static async getCar(carId) {
    return carService.getCar(carId).then(res => {
      return res.data || {}
    })
  }

  static async updateCar(carId, data) {
    return carService.updateCar(carId, data).then(res => {
      return res.data || {}
    })
  }

  static async getCarRentalHistory(carId) {
    return carService.getCarRentalHistory(carId).then(res => {
      return res.data || {}
    })
  }

}
export const getCars = CarActions.getCars;
export const getCarMaintenanceHistory = CarActions.getCarMaintenanceHistory;
export const addCar = CarActions.addCar;
export const getCar = CarActions.getCar;
export const updateCar = CarActions.updateCar;
export const getCarRentalHistory = CarActions.getCarRentalHistory;
