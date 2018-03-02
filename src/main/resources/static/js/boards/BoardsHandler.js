import Template from '../support/template.js';
import MSG from '../../message.json';
import { _, boardUtils, API } from '../support/Utils.js';

class BoardsHandler {
    constructor() {
        this.modalDiv = _.$('#modal');
    }

    toggleModal() {
        this.modalDiv.classList.toggle('open');
        _.$(".board-name").value="";
        _.$(".warning").innerHTML = "";
    }

    saveBoard() {
        const boardName = _.$('.board-name').value;
        if (boardName.length > 20) {
            this.showWarning(MSG.NAME.LENGTH);
            return;
        }

        const parameters = {
            "name": boardName
        };

        _.request(API.BOARDS.MYBOARD, "POST", parameters).then(this.appendBoard.bind(this));
    }

    printBoards(res) {
        const boards = res.content;
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
            console.log(res);
            res.forEach((content) => {
                this.showWarning(content.message);
            });
        }
    }

    showWarning(message) {
        const warning = _.$('.warning');
        warning.innerHTML = message;
        warning.style.display = 'block';
    }

    boardsEventHandler() {
        _.eventHandler(".add-board-btn", "click", this.toggleModal.bind(this));
        _.eventHandler(".close-modal", "click", this.toggleModal.bind(this));
        _.eventHandler(".add-board-form", "submit", (e) => {
            e.preventDefault();
            this.saveBoard();
        });
    }

}

export default BoardsHandler;