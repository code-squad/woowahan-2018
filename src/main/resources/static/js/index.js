import { _ } from './support/Utils.js';
import { UserController, UserResponseHandler } from './User.js';
import { BoardsController, BoardsViewHandler } from './boards.js';
import { BoardController, BoardViewHandler } from './board.js';

const userController = new UserController();
const userResponseHandler = new UserResponseHandler();
const boardsController = new BoardsController();
const boardsViewHandler = new BoardsViewHandler();
const boardController = new BoardController();
const boardViewHandler = new BoardViewHandler();

// user관련 이벤트
_.eventHandler(".login-form", "submit", (e) => userController.login(e, userResponseHandler.login));
_.eventHandler(".signup-form", "submit", (e) => userController.signup(e, userResponseHandler.signup));
_.eventHandler(".logout-button", "click", (e) => userController.logout(e, userResponseHandler.logout));

// 회원가입 유효성 체크
_.eventHandler(".signup-form", "focusout", userController.validateValue.bind(userController));

// myBoards 관련 이벤트
boardsController.domLoaded(boardsViewHandler.printBoards);
_.eventHandler(".add-board-btn", "click", boardsViewHandler.openModal.bind(boardsViewHandler));
_.eventHandler(".close-modal", "click", boardsViewHandler.closeModal.bind(boardsViewHandler));
_.eventHandler(".save-board", "click", (e) => boardsController.saveBoard(boardsViewHandler.appendBoard.bind(boardsViewHandler)));

// board 관련 이벤트
boardController.domLoaded(boardViewHandler.printBoard.bind(boardViewHandler));
_.eventHandler(".add-deck-btn", "click", boardViewHandler.openDeckForm.bind(boardViewHandler));
_.eventHandler(".cancel-deck", "click", (e) => {
    e.preventDefault();
    boardViewHandler.closeDeckForm();
})
_.eventHandler(".save-deck", "click", (e) => boardController.saveDeck(e, boardViewHandler.appendDeck.bind(boardViewHandler)))






_.eventHandler(".deck-list", "click", (e) => {
    console.log(e)
})