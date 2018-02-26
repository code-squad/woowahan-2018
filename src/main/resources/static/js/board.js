import { _, boardUtils, API } from './support/Utils.js'

const params = window.location.search.substr(1);
const boardId = params.split("=")[1];

class BoardController{
    domLoaded(callback) {
        if (window.location.pathname !== "/board.html") {
            return;
        }

        document.addEventListener("DOMContentLoaded", () => {
            this.getBoard(callback);
        });
    }

    saveDeck(e, callback) {
        const nameDom = _.$("#add-deck");
        e.preventDefault();

        const data = {
            "name": nameDom.value
        };

        _.ajax(API.BOARDS.DECKS(boardId), "POST", data).then(callback);
    }

    saveCard(deckId, callback) {
        const data = {
            "text": document.getElementById(`card-title-${deckId}`).value
        };

        _.ajax(API.BOARDS.CARDS(deckId), "POST", data).then(callback);
    }

    getBoard(callback) {
        _.ajax(API.BOARDS.BOARD(boardId), "GET").then(callback);
    }
}

class BoardViewHandler {
    constructor() {


        this.errorMessage = _.$(".error-message");
        this.boardName = _.$(".board-name");
        this.deckViewHandler = new DeckViewHandler();
    }

    printBoardName(boardName) {
        this.boardName.innerHTML = boardName
        this.errorMessage.innerHTML = "";
    }

    printBoard(res) {
        if(res.status === "OK") {
            const board = res.content;
            this.printBoardName(board.name);
            this.deckViewHandler.printDecks(board.decks);


            _.eventHandler(".add-deck-btn", "click", this.deckViewHandler.toggleDeckForm.bind(this.deckViewHandler));
            _.eventHandler(".cancel-deck", "click", (e) => {
                e.preventDefault();
                this.deckViewHandler.toggleDeckForm().bind(this.deckViewHandler);
            })
            _.eventHandler(".save-deck", "click", (e) => this.deckViewHandler.saveDeck(e, this.deckViewHandler.appendDeck.bind(this.deckViewHandler)))



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
}

class CardViewHandler {
    constructor() {
        this.boardController = new BoardController();
    }

    toggleCardForm(id) {
        _.$(`#add-card-form-${id}`).classList.toggle("open");
        _.$(`#add-card-btn-${id}`).classList.toggle("close");
        _.$(`#card-title-${id}`).value = "";
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
        const handler = {
            "add-card-btn"() {
                this.toggleCardForm(deckId);
            },
            "btn waves-effect waves-light cancel-card"() {
                this.toggleCardForm(deckId);
            },
            "btn waves-effect waves-light save-card"() {
                this.boardController.saveCard(deckId, this.appendCard);
                this.toggleCardForm(deckId);
            }
        }

        _.$(".deck-list").addEventListener("click", (e) => {
            if (handler[e.target.className]) {
                handler[e.target.className].call(this);
            }
        })
    }

}

export { BoardController, BoardViewHandler };