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
        const cardTitleDom = _.$(`#card-title-${id}`);
        cardTitleDom.value = "";
        cardTitleDom.focus();
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
        }, "");

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
                this.cardModalHandler.getCardDetail(cardId, this.cardModalHandler.openCardModal.bind(this.cardModalHandler));
			}
		});
	}

	cardDragEventHandler() {
	    function getTargetDeck(target) {
			const targetDeck = target.closest(".deck-cards");
			if (targetDeck) {
			    return targetDeck;
            }

            try {
                return target.closest(".deck-wrapper").querySelector(".deck-cards");
            } catch (e) {
                return;
            }
	    }

		_.eventHandler(".deck-list", "dragstart", (e) => {
			const targetCard = e.target.closest(".deck-card");
			if (!targetCard) return false;

			const dataTransfer = e.dataTransfer;

			dataTransfer.effectAllowed = "move";
			dataTransfer.setData("Data", targetCard.getAttribute("id"));
			dataTransfer.setDragImage(targetCard, targetCard.clientWidth / 2, targetCard.clientHeight / 2);
			return true;
		});

		_.eventHandler(".deck-list", "dragend", (e) => {
			e.dataTransfer.clearData("Data");
			return true;
		});

		_.eventHandler(".deck-list", "dragenter", (e) => {
            const targetDeck = getTargetDeck(e.target);
            if (!targetDeck) {
                return false
            }

			e.preventDefault();
			return true;
		});

		_.eventHandler(".deck-list", "dragover", (e) => {
			const targetDeck = getTargetDeck(e.target);
			if (!targetDeck) {
			    return true;
			}

			e.preventDefault();
			return false;
		});

		_.eventHandler(".deck-list", "drop", (e) => {
			const targetDeck = getTargetDeck(e.target);

			if (!targetDeck) {
			    return true;
			}

			const standardCard = e.target.closest(".deck-card");
			const targetCard = _.$(`#${e.dataTransfer.getData("Data")}`);

			e.stopPropagation();

			const params = {
				deckId: parseInt(targetDeck.dataset.deckId),
                standardCardId: -1,
                standardType: false
			};

			if (standardCard && e.offsetY < targetCard.clientHeight / 2) {
				params.standardCardId = parseInt(standardCard.dataset.cardId);
				params.standardType = true;
				targetDeck.insertBefore(targetCard, standardCard);
			} else if (standardCard && standardCard.nextSibling) {
				params.standardCardId = parseInt(standardCard.dataset.cardId);
				params.standardType = false;
				targetDeck.insertBefore(targetCard, standardCard.nextSibling);
			} else {
				targetDeck.appendChild(targetCard);
			}

			_.request(API.BOARDS.CARD_MOVE(targetCard.dataset.cardId), "PUT", params);
			return false;
		});
	}

}

export default CardHandler;