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

        _.ajax(API.BOARDS.CARDS(boardId, deckId), "POST", data).then(callback);
    }

    getBoard(callback) {
        _.ajax(API.BOARDS.BOARD(boardId), "GET").then(callback);
    }
}

class BoardViewHandler {
    constructor() {
        this.addDeckForm = _.$(".add-deck-form");
        this.nameDom = _.$("#add-deck");
        this.errorMessage = _.$(".error-message");
        this.boardName = _.$(".board-name");
        this.deckList = _.$(".deck-list");
    }

    openDeckForm() {
        this.addDeckForm.classList.add("open");
    }

    closeDeckForm() {
        this.addDeckForm.classList.remove("open");
        this.nameDom.value = "";
    }

    openCardForm(id) {
        _.$(`#add-card-form-${id}`).classList.add("open");
        _.$(`#add-card-btn-${id}`).classList.add("close");
    }

    closeCardForm(id) {
        _.$(`#add-card-form-${id}`).classList.remove("open");
        _.$(`#add-card-btn-${id}`).classList.remove("close");
        _.$(`#card-title-${id}`).value = "";
    }

    appendCard(res) {
        let status = res.status;
        const deckId = res.content.deckId;
        const card = res.content.card

        if (status === "OK") {
            document
                .getElementById(`deck-cards-${deckId}`)
                .insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.card, {"value" : card.text}));
        } else {
            errorMessage.innerHTML = res.message;
        }
    }

    printBoardName(boardName) {
        this.boardName.innerHTML = boardName
        this.errorMessage.innerHTML = "";
    }

    printBoard(res) {
        if(res.status === "OK") {
            const board = res.content;
            this.printBoardName(board.name);
            this.printDecks(board.decks);
        } else {
            this.errorMessage.innerHTML = res.message;
        }
    }

    appendDeck(res) {
        let status = res.status;

        if (status === "OK") {
            this.deckList.insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.deck, {"id" : res.content.id, "value": res.content.name}));
            initCardButtons(res.content.id);
            this.errorMessage.innerHTML = "";
        } else {
            this.errorMessage.innerHTML = res.message;
        }

        this.closeDeckForm();
    }

    printDecks(decks) {
        decks.forEach((deck) => {
            this.deckList.insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.deck, {"id" : deck.id, "value" : deck.name}));
            this.printCards(deck.id, deck.cards);
            initCardButtons(deck.id);
        });
        this.errorMessage.innerHTML = "";
    }

    printCards(deckId, cards) {
        cards.forEach((card) => {
            _.$(`#deck-cards-${deckId}`).insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.card, {"value" : card.text}));
        });
    }
}

function initCardButtons(deckId) {
    const boardController = new BoardController();
    const boardViewHandler = new BoardViewHandler();
    document
        .getElementById(`add-card-btn-${deckId}`)
        .addEventListener("click", () => {
            boardViewHandler.openCardForm(deckId);
        });

    document
        .getElementById(`save-card-${deckId}`)
        .addEventListener("click", () => {
            boardController.saveCard(deckId, boardViewHandler.appendCard);
            boardViewHandler.closeCardForm(deckId);
        });

    document
        .getElementById(`cancel-card-${deckId}`)
        .addEventListener("click", () => {
            boardViewHandler.closeCardForm(deckId);
        });
}

export { BoardController, BoardViewHandler };