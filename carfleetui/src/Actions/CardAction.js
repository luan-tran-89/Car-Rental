import cardService from "../Services/CardService"

export default class CardAction {

    static async addCard(data) {
        return cardService.addCard(data).then(res => {
            return res.data || {}
        })
    }

}

export const addCard = CardAction.addCard;

