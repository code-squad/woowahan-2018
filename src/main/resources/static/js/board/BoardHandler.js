import DeckHandler from './DeckHandler.js';
import { _ } from '../support/Utils.js';

class BoardHandler {
    constructor(boardId) {
        this.deckHandler = new DeckHandler(boardId);
        this.errorMessage = _.$(".error-message");
        this.boardId = boardId;
    }

    printBoardName(boardName) {
        const boardNameDom = _.$(".board-name");
        boardNameDom.innerHTML = boardName;
        this.errorMessage.innerHTML = "";
    }

    printBoard(res) {
        console.log(res)
        if(res.status === "OK") {
            const board = res.content;
            this.printBoardName(board.name);
            this.deckHandler.printDecks(board.decks);
            this.deckHandler.deckEventHandler();
        } else {
            this.errorMessage.innerHTML = res.message;
        }
    }
}

export default BoardHandler;