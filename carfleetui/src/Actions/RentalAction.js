import rentalService from "../Services/RentalService"

export default class RentalActions {
    static async customerReserveCar(data) {
        return rentalService.customerReserveCar(data).then(res => {
          return res.data || {}
        })
      }

    static async getCustomerCarReservations(userId) {
      return rentalService.getCustomerCarReservations(userId).then(res => {
        return res.data || {}
      })
    } 
    static async addCustomerRental(data) {
      return rentalService.addCustomerRental(data).then(res => {
        return res.data || {}
      })
    } 
}

export const customerReserveCar = RentalActions.customerReserveCar;
export const getCustomerCarReservations = RentalActions.getCustomerCarReservations;
export const addCustomerRental = RentalActions.addCustomerRental;