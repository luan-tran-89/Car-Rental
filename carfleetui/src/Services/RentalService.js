import APIs from "../Common/API";
import defaultRestApi from "../Common/RestAPiInstance";

export default class RentalService {

    static customerReserveCar = (data) => {
        
      return defaultRestApi.post(APIs.customerReserveCar(), data, {
        headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }});
    }

    static getCustomerCarReservations = (userId) => {
      return defaultRestApi.get(APIs.getCustomerCarReservations(userId), {
        headers:{
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      })
    }

    static getCustomerCarReservations = (userId) => {
      return defaultRestApi.get(APIs.getCustomerCarReservations(userId), {
        headers:{
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      })
    }
    static addCustomerRental = (data) => {
      return defaultRestApi.post(APIs.addCustomerRental(), data, {
        headers:{
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      })
    }
}
