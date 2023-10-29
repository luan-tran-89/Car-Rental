import defaultRestApi from '../Common/RestAPiInstance';
import APIs from '../Common/API';


export default class CarService {

  static getCars = () => {
    return defaultRestApi.get(APIs.getCars());
  }

  static getCarMaintenanceHistory = (id) => {
    return defaultRestApi.get(APIs.getCarMaintenanceHistory(id), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
  }

  static addCar = (data) => {
    return defaultRestApi.post(APIs.addCar(), data, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
  }

  static getCar = (carId) => {
    return defaultRestApi.get(APIs.getCar(carId), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
  }

  static updateCar = (carId, data) => {
    return defaultRestApi.patch(APIs.updateCar(carId), data, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
  }

  static getCarRentalHistory = (carId) => {
    return defaultRestApi.get(APIs.getCarRentalHistory(carId), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
  }
}