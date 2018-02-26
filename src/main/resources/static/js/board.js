import { _, boardUtils, API } from './support/Utils.js'

const params = new URLSearchParams(document.location.search.substring(1));
const boardId = params.get("boardId");

class BoardController{
    domLoaded(callback) {
        if (window.location.pathname !== "/board.html") {
            return;
        }

        document.addEventListener("DOMContentLoaded", () => {
            this.getBoard(callback);
        });
    }

    getBoard(callback) {
        _.ajax(API.BOARDS.BOARD(boardId), "GET").then(callback);
    }
}

class BoardViewHandler {
    constructor() {
        this.deckViewHandler = new DeckViewHandler();
        this.errorMessage = _.$(".error-message");
    }

    printBoardName(boardName) {
        const boardNameDom = _.$(".board-name");
        boardNameDom.innerHTML = boardName;
        this.errorMessage.innerHTML = "";
    }

    printBoard(res) {
        if(res.status === "OK") {
            const board = res.content;
            this.printBoardName(board.name);
            this.deckViewHandler.printDecks(board.decks);
            this.deckViewHandler.deckEventHandler();
        } else {
            this.errorMessage.innerHTML = res.message;
        }
    }

}

class DeckViewHandler {
    constructor() {
        this.cardViewHandler = new CardViewHandler();
        this.deckList = _.$(".deck-list");
        this.errorMessage = _.$(".error-message");
    }

    toggleDeckForm() {
        _.$(".add-deck-form").classList.toggle("open");
        _.$("#add-deck").value = "";
    }

    saveDeck(e, callback) {
        const nameDom = _.$("#add-deck");

        const data = {
            "name": nameDom.value
        };

        _.ajax(API.BOARDS.DECKS(boardId), "POST", data).then(callback);
    }

    appendDeck(res) {
        const status = res.status;

        if (status === "OK") {
            this.deckList.insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.deck, {"id" : res.content.id, "value": res.content.name}));
            this.cardViewHandler.cardEventHandler(res.content.id);
            this.errorMessage.innerHTML = "";
        } else {
            this.errorMessage.innerHTML = res.message;
        }

        this.toggleDeckForm();
    }

    printDecks(decks) {
        decks.forEach((deck) => {
            this.deckList.insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.deck, {"id" : deck.id, "value" : deck.name}));
            this.cardViewHandler.printCards(deck.id, deck.cards);
            this.cardViewHandler.cardEventHandler(deck.id);
        });
        this.errorMessage.innerHTML = "";
    }

    deckEventHandler() {
        _.eventHandler(".add-deck-area", "click", (e, callback) => {
            e.preventDefault();

            if (e.target.id === "save-deck-btn") {
                this.saveDeck(e, this.appendDeck.bind(this))
            } else if (e.target.id === "add-deck-btn") {
                this.toggleDeckForm();
            } else if (e.target.id === "cancel-deck-btn") {
                this.toggleDeckForm();
            }
        })
    }
}

class CardViewHandler {
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

export { BoardController, BoardViewHandler };