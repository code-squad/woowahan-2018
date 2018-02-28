import Template from '../support/template.js';
import CardModalHandler from './CardModalHandler.js';
import { _, boardUtils, API } from '../support/Utils.js';

class CardHandler {
    constructor(boardId) {
        this.boardId = boardId;
        this.cardModalHandler = new CardModalHandler();
    }

    toggleCardForm(id) {
        _.$(`#add-card-form-${id}`).classList.toggle("open");
        _.$(`#add-card-btn-${id}`).classList.toggle("close");
        _.$(`#card-title-${id}`).value = "";
    }

    saveCard(deckId, callback) {
        const data = {
            "text": _.$(`#card-title-${deckId}`).value,
            "deckId" : deckId

        };

        _.request(API.BOARDS.CARDS(), "POST", data).then(callback);
    }

    appendCard(res) {
        const status = res.status;
        const deckId = res.content.deckId;
        const card = res.content.card;
        const errorMessage = _.$(".error-message");

        if (status === "OK") {
            const template = this.printCard(deckId, card);
            _.$(`#deck-cards-${deckId}`).insertAdjacentHTML("beforeend", template);
        } else {
            errorMessage.innerHTML = res.message;
        }
    }

    printCards(deckId, cards) {
        const html = cards.reduce((html, card) => {
            return html + this.printCard(deckId, card);
        }, "")

        _.$(`#deck-cards-${deckId}`).insertAdjacentHTML("beforeend", html);
    }

    printCard(deckId, card) {
        const data = {
            "id": card.id,
            "value": card.text
        };
        return boardUtils.createTemplate(Template.card, data);
    }

    cardEventHandler(deckId) {
        _.eventHandler(".deck-list", "click", (e) => {
            if (e.target.id === "add-card-btn-" + deckId) {
                this.toggleCardForm(deckId);
            } else if (e.target.id === "cancel-card-" + deckId) {
                this.toggleCardForm(deckId);
            } else if (e.target.id === "save-card-" + deckId) {
                this.saveCard(deckId, this.appendCard.bind(this));
                this.toggleCardForm(deckId);
            } else if (e.target.classList.contains("modalLink")) {
                const cardId = e.target.id;

                this.cardModalHandler.setDeckId(deckId);
                this.cardModalHandler.setCardId(cardId);
                this.cardModalHandler.getCardDetail(deckId, cardId, this.cardModalHandler.openCardModal.bind(this.cardModalHandler));
            }
        });
    }
}

export default CardHandler;