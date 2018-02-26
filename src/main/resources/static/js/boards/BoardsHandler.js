import Template from '../support/template.js';
import { _, boardUtils, API } from '../support/Utils.js';

class BoardsHandler {
    constructor() {
        this.modalDiv = _.$('#modal');
    }

    toggleModal() {
        this.modalDiv.classList.toggle('open');
    }

    saveBoard() {
        const nameDom = _.$('.board-name');
        const parameters = {
            "name": nameDom.value
        }

        _.ajax(API.BOARDS.MYBOARD, "POST", parameters).then(this.appendBoard.bind(this));
    }

    printBoards(res) {
        const boards = res.content.boards;
        const boardListDom = _.$('.board-list');

        boards.forEach((item) => {
            boardListDom.innerHTML += boardUtils.createTemplate(Template.board, {'id': item.id, 'name': item.name});
        })

        this.boardsEventHandler();
    }

    appendBoard(res) {
        const status = res.status;
        const nameDom = _.$('.board-name');
        const boardListDom = _.$('.board-list');

        if (status === "OK") {
            boardListDom.insertAdjacentHTML('beforeend', boardUtils.createTemplate(Template.board, {'id' : res.content.id, 'name': res.content.name}));
            this.toggleModal();
            nameDom.value = "";
        } else {
            const warning = _.$('.warning');
            warning.innerHTML = res.message;
            warning.style.display = 'block';
        }
    }

    boardsEventHandler() {
        _.eventHandler(".add-board-btn", "click", this.toggleModal.bind(this));
        _.eventHandler(".close-modal", "click", this.toggleModal.bind(this));
        _.eventHandler(".save-board", "click", this.saveBoard.bind(this));
    }

}

export default BoardsHandler;