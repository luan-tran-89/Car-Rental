import paymentService from "../Services/PaymentService"

export default class PaymentAction {
    static async addPaymentMethod(data) {
        
        return paymentService.addPaymentMethod(data).then(res => {
          return res.data || {}
        })
      }

      static async getCustomerPaymentMethods(userId) {
        
        return paymentService.getCustomerPaymentMethods(userId).then(res => {
          return res.data || {}
        })
      }

      static async updatePaymentMethod(paymentMethodId, data) {
        
        return paymentService.updatePaymentMethod(paymentMethodId,data).then(res => {
          return res.data || {}
        })
      }

      static async getCustomerPaymentMethod(paymentMethodId) {
        
        return paymentService.getCustomerPaymentMethod(paymentMethodId).then(res => {
          return res.data || {}
        })
      }
}

export const addPaymentMethod = PaymentAction.addPaymentMethod;
export const getCustomerPaymentMethods = PaymentAction.getCustomerPaymentMethods;
export const updatePaymentMethod = PaymentAction.updatePaymentMethod;
export const getCustomerPaymentMethod = PaymentAction.getCustomerPaymentMethod;