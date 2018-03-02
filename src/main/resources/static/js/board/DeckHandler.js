import Template from '../support/template.js';
import {_, boardUtils, API} from '../support/Utils.js';
import MSG from '../../message.json';
import CardHandler from './CardHandler.js';

class DeckHandler {
    constructor(boardId) {
        this.cardHandler = new CardHandler(boardId);
        this.deckList = _.$(".deck-list");
        this.errorMessage = _.$(".error-message");
        this.boardId = boardId;
        this.deckWarning = _.$(".deck-warning");
    }

    toggleDeckForm() {
        _.$(".add-deck-form").classList.toggle("open");
        const addDeckDom = _.$("#add-deck");
        addDeckDom.value = "";
        addDeckDom.focus();

        this.deckWarning.innerHTML = "";
    }

    showDeckWarning(message) {
        this.deckWarning.innerHTML = message;
    }

    saveDeck(e, callback) {
        const deckName = _.$("#add-deck").value;
        if (deckName.length === 0) {
            this.showDeckWarning(MSG.NAME.EMPTY);
            return;
        }

        if (deckName.length > 20) {
            this.showDeckWarning(MSG.NAME.LENGTH);
            return;
        }

        const data = {
            "name": deckName,
            "boardId" : this.boardId
        };

        _.request(API.BOARDS.DECKS(), "POST", data).then(callback);
    }

    appendDeck(res) {
        const status = res.status;
        this.errorMessage.innerHTML = "";

        if (status === "OK") {
            this.deckList.insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.deck, {
                "id": res.content.id,
                "value": res.content.name
            }));
            this.cardHandler.cardEventHandler(res.content.id);
        } else {
            res.forEach((content) => {
                this.errorMessage.innerHTML = content.message;
            })
        }

        this.toggleDeckForm();
    }

    printDecks(decks) {
        decks.forEach((deck) => {
            this.deckList.insertAdjacentHTML("beforeend", boardUtils.createTemplate(Template.deck, {
                "id": deck.id,
                "value": deck.name
            }));
            this.cardHandler.printCards(deck.id, deck.cards);
            this.cardHandler.cardEventHandler(deck.id);
        });

        this.cardHandler.cardDragEventHandler();
        this.errorMessage.innerHTML = "";
    }

    deckEventHandler() {
        _.eventHandler(".add-deck-area", "click", (e) => {
            e.preventDefault();

            if (e.target.id === "button-deck-save") {
                this.saveDeck(e, this.appendDeck.bind(this))
            } else if (e.target.id === "button-deck-add") {
                this.toggleDeckForm();
            } else if (e.target.id === "button-deck-cancel") {
                this.toggleDeckForm();
            }
        })
    }
}

export default DeckHandler;