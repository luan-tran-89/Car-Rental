
const apiGatewayURL = 'http://localhost:8889';

const authService = '/auth-service/uaa';

const userService = '/user-service/users';

const carService = '/car-fleet-service/car-fleet';

const rentalService = '/rental-service/rentals';

const paymentService = `/payment-service`

// /maintenance-history?carId=1

// const getManagers = '/user-service/users/manager/list'

// const addManagerService = "/user-service/users/manager/add"

const APIs = {

  getToken: function () {
    return `${apiGatewayURL}${authService}`;
  },

  getRefreshToken: function() {
    return `${apiGatewayURL}${authService}/refreshToken`
  },
  updateUser: function(userId) {
    return `${apiGatewayURL}${userService}/update`
  },


  register: function () {
    return `${apiGatewayURL}${userService}/register`;
  },

  getManagers: function() {
    return `${apiGatewayURL}${userService}/manager/list`;
  },

  getManager: function(email) {
    return `${apiGatewayURL}${userService}?email=${email}`
  },

  updateManager: function(userId) {
    return `${apiGatewayURL}${userService}/manager/update/${userId}`
  },

  getCustomerRentalHisotry: function() {
    return `${apiGatewayURL}${userService}/rental-history`;
  },

  getCustomers: function() {
    // console.log(`${apiGatewayURL}${userService}/list`)f
    return `${apiGatewayURL}${userService}/list`;
  },
  getCustomerById: function(userId) {
    // console.log(`${apiGatewayURL}${userService}/list`)f
    return `${apiGatewayURL}${userService}/${userId}`;
  },
  getCar: function(carId) {
    return `${apiGatewayURL}${carService}/car/${carId}`;
  },
  getCars: function() {
    // console.log(`${apiGatewayURL}${userService}/list`)
    // console.log(`${apiGatewayURL}${carService}/search`)
    return `${apiGatewayURL}${carService}/car/search`;
  },
  updateCar: function(carId) {
    return `${apiGatewayURL}${carService}/${carId}`;
  },

  getCustomerCarReservations: function(userId) {
    return `${apiGatewayURL}${rentalService}/user/${userId}/reservations`
  },

  addManager: function() {
    return `${apiGatewayURL}${userService}/manager/add`
  },

  
  getCarMaintenanceHistory: function(id) {
    return `${apiGatewayURL}${carService}/maintenance-history?carId=${id}`
  },

  getCustomer: function(email) {
    return `${apiGatewayURL}${userService}?email=${email}`
  },

  addPaymentMethod: function() {
    return `${apiGatewayURL}${paymentService}/payment-methods`
  },

  updatePaymentMethod: function(paymentMethodId) {
    return `${apiGatewayURL}${paymentService}/payment-methods/${paymentMethodId}`
  },

  getCustomerPaymentMethods: function(userId) {
    return `${apiGatewayURL}${paymentService}/payment-methods/users/${userId}`
  },
  getCustomerPaymentMethod: function(paymentMethodId) {
    return `${apiGatewayURL}${paymentService}/payment-methods/${paymentMethodId}`
  },

  customerReserveCar: function() {
    return `${apiGatewayURL}${rentalService}/reserve`
  },

  addCustomerRental: function() {
    return `${apiGatewayURL}${rentalService}/create`//change this one to finalize, or direct 
  },

  addCar: function() {
    return `${apiGatewayURL}${carService}`;
  },
  getCarRentalHistory: function(carId) {
   return `${apiGatewayURL}${carService}/rental-history/${carId}`;
  },
  getCustomerCarRentalHistory: function(userId) {
    return `${apiGatewayURL}${carService}/rental-history/user/${userId}`;
  }


}

export default APIs;