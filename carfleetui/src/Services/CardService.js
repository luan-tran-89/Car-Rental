import defaultRestApi from "../Common/RestAPiInstance";
import APIs from "../Common/API";

export default class CardService {
    
    static addCard = (data) => {
        return defaultRestApi.post(APIs.addCard(), data, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        }) 
    }
}