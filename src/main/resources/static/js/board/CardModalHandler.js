import {_, boardUtils, API} from '../support/Utils.js';

class CardModalHandler {
    constructor(deckId, cardId) {
        this.cardModal = _.$("#card-modal");
        this.cardModalEventHandler();
    }

    setDeckId(deckId) {
        this.deckId = deckId;
    }

    setCardId(cardId) {
        this.cardId = cardId;
    }

    getCardDetail(deckId, cardId, callback) {
        _.ajax(API.BOARDS.CARD(cardId), 'GET').then(callback);
    }

    editDescription(deckId, cardId, description, callback) {
        const data = {
            "description" : description
        }
        _.ajax(API.BOARDS.CARD_DESCRIPTION(cardId), 'PUT', data).then(callback);
    }

    printDescription(res) {
        console.log(res);
        const card = res.content;

        _.$(".card-description").innerHTML = card.description;
        this.closeDescriptionField();
    }

    openCardModal(res) {
        const card = res.content;

        _.$(".card-title-in-modal").innerHTML = card.text;
        _.$(".deck-name").innerHTML = card.deckName;
        _.$(".card-description").innerHTML = card.description;

        this.cardModal.classList.add("open");
        this.closeDescriptionField();
    }

    closeCardModal() {
        this.cardModal.classList.remove("open");
    }

    openDescriptionField() {
        _.$(".card-description-textarea").value = _.$(".card-description").innerHTML;
        _.$(".card-description").classList.add("close");
        _.$(".card-description-edit").classList.add("open");
        _.$(".card-description-edit-btn").classList.add("close");
    }

    closeDescriptionField() {
        _.$(".card-description").classList.remove("close");
        _.$(".card-description-edit").classList.remove("open");
        _.$(".card-description-edit-btn").classList.remove("close");
    }

    cardModalEventHandler() {
        _.eventHandler("#card-modal", "click", (e) => {
            e.preventDefault();

            if(e.target.classList.contains("close-modal")) {
                this.closeCardModal();
            } else if (e.target.classList.contains("card-description-edit-btn")) {
                this.openDescriptionField();
            } else if (e.target.classList.contains("card-edit-close")) {
                this.closeDescriptionField();
            } else if (e.target.classList.contains("card-edit-save")) {
                const description = _.$(".card-description-textarea").value;
                this.editDescription(this.deckId, this.cardId, description, this.printDescription.bind(this));
            }
        });
    }
}

export default CardModalHandler;