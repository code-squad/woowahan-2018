import { _, boardUtils, API } from '../support/Utils.js';

class CardHandler {
    toggleCardForm(id) {
        _.$(`#add-card-form-${id}`).classList.toggle("open");
        _.$(`#add-card-btn-${id}`).classList.toggle("close");
        _.$(`#card-title-${id}`).value = "";
    }

    saveCard(deckId, callback) {
        const data = {
            "text": document.getElementById(`card-title-${deckId}`).value
        };

        _.ajax(API.BOARDS.CARDS(deckId), "POST", data).then(callback);
    }

    appendCard(res) {
        const status = res.status;
        const deckId = res.content.deckId;
        const card = res.content.card
        const errorMessage = _.$(".error-message");

        if (status === "OK") {
            _.$(`#deck-cards-${deckId}`).insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.card, {"value": card.text}));
        } else {
            errorMessage.innerHTML = res.message;
        }
    }

    printCards(deckId, cards) {
        cards.forEach((card) => {
            _.$(`#deck-cards-${deckId}`).insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.card, {"value" : card.text}));
        });
    }

    cardEventHandler(deckId) {
        _.eventHandler(".deck-list", "click", (e) => {
            if (e.target.id === "add-card-btn-" + deckId) {
                this.toggleCardForm(deckId);
            } else if (e.target.id === "cancel-card-" + deckId) {
                this.toggleCardForm(deckId);
            } else if (e.target.id === "save-card-" + deckId) {
                this.saveCard(deckId, this.appendCard);
                this.toggleCardForm(deckId);
            }
        })
    }
}

export default CardHandler;