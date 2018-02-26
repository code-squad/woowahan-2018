import CardHandler from './CardHandler.js';
import { _, boardUtils, API } from '../support/Utils.js';

class DeckHandler {
    constructor(boardId) {
        this.cardHandler = new CardHandler();
        this.deckList = _.$(".deck-list");
        this.errorMessage = _.$(".error-message");
        this.boardId = boardId;
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

        _.ajax(API.BOARDS.DECKS(this.boardId), "POST", data).then(callback);
    }

    appendDeck(res) {
        const status = res.status;

        if (status === "OK") {
            this.deckList.insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.deck, {"id" : res.content.id, "value": res.content.name}));
            this.cardHandler.cardEventHandler(res.content.id);
            this.errorMessage.innerHTML = "";
        } else {
            this.errorMessage.innerHTML = res.message;
        }

        this.toggleDeckForm();
    }

    printDecks(decks) {
        decks.forEach((deck) => {
            this.deckList.insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.deck, {"id" : deck.id, "value" : deck.name}));
            this.cardHandler.printCards(deck.id, deck.cards);
            this.cardHandler.cardEventHandler(deck.id);
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

export default DeckHandler;