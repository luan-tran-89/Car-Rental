import APIs from "../Common/API";
import defaultRestApi from "../Common/RestAPiInstance";

export default class PaymentService { 
    static addPaymentMethod = (data) => {
        
        return defaultRestApi.post(APIs.addPaymentMethod(), data, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
      }

      static getCustomerPaymentMethods = (userId) => {
        return defaultRestApi.get(APIs.getCustomerPaymentMethods(userId), {
          
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
      }

      static updatePaymentMethod = (userId, data) => {
        return defaultRestApi.put(APIs.updatePaymentMethod(userId), data, {
          
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
      }

    
      static getCustomerPaymentMethod = (paymentMethodId) => {
        return defaultRestApi.get(APIs.getCustomerPaymentMethod(paymentMethodId), {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
      }
}

