import { _, boardUtils, API } from '../support/Utils.js';
import BoardsHandler from './BoardsHandler.js';

class BoardsController {
    constructor() {
        this.boardsHandler = new BoardsHandler();
    }

    domLoaded() {
        if (window.location.pathname !== "/boards.html") {
            return;
        }

        document.addEventListener("DOMcontentLoaded", this.getBoards());
    }

    getBoards() {
        _.request(API.BOARDS.MYBOARD, "GET").then(this.boardsHandler.printBoards.bind(this.boardsHandler));
    }

}

export default BoardsController;