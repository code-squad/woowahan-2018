import DeckHandler from './DeckHandler.js';
import { _, API } from '../support/Utils.js';

class BoardHandler {
    constructor(boardId) {
        this.deckHandler = new DeckHandler(boardId);
        this.errorMessage = _.$(".error-message");
        this.boardId = boardId;
    }

    toggleMembersForm() {
        _.$(".member-list").classList.toggle("open");
        _.$("#button-member-add").value = "";
        _.$(".error-member-message").innerHTML = "";
    }

    addMembers() {
        const email = _.$("#button-member-add").value;
        const data = {
            "email": email
        }

        _.request(API.BOARDS.ADDMEMBER(this.boardId), "POST", data).then(this.addMembersResponseHandler.bind(this));
    }

    addMembersResponseHandler(res) {
        if (res.status === "OK") {
            this.toggleMembersForm();
            this.board.members = res.content;
        } else {
            _.$(".error-member-message").innerHTML = res.message;
        }
    }

    printBoardName(boardName) {
        const boardNameDom = _.$(".board-name");
        boardNameDom.innerHTML = boardName;

        this.errorMessage.innerHTML = "";
    }

    printBoard(res) {
        if(res.status === "OK") {
            const board = res.content;

            this.board = res.content;
            this.printBoardName(board.name);
            this.deckHandler.printDecks(board.decks);
            this.boardEventHandler();
            this.deckHandler.deckEventHandler();
        } else {
            this.errorMessage.innerHTML = res.message;
        }
    }

    printMembers() {
        const html = this.board.members.reduce((html, member) => {
            return html + `<li><span>email: ${member.email}</span></br><span>name: ${member.name}</span></li>`
        }, "")

        _.$(".exist-member-list").innerHTML = html;
    }

    boardEventHandler() {
        _.eventHandler("#button-board-members", "click", (e) => {
            this.toggleMembersForm();
            this.printMembers();
        })

        _.eventHandler(".add-member-form", "submit", (e) => {
            e.preventDefault();
            this.addMembers();
        })
    }
}

export default BoardHandler;