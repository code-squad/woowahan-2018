import { _, boardUtils, API } from '../support/Utils.js'
import BoardHandler from './BoardHandler.js';

class BoardController {
    domLoaded(callback) {
        if (window.location.pathname !== "/board.html") {
            return;
        }

        document.addEventListener("DOMContentLoaded", () => {
            const params = new URLSearchParams(document.location.search.substring(1));
            const boardId = params.get("boardId");
            const boardHandler = new BoardHandler(boardId);
            this.getBoard(boardId, boardHandler.printBoard.bind(boardHandler));
        });
    }

    getBoard(boardId, callback) {
        _.ajax(API.BOARDS.BOARD(boardId), "GET").then(callback);
    }
}

export default BoardController;