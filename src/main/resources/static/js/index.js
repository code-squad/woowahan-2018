import { _ } from './support/Utils.js';
import { UserController, UserViewHandler } from './User.js';
import { BoardsController, BoardsViewHandler } from './boards.js';
import BoardController from './board/BoardController.js';

const userController = new UserController();
const userViewHandler = new UserViewHandler();
const boardsController = new BoardsController();
const boardsViewHandler = new BoardsViewHandler();
const boardController = new BoardController();

// user관련 이벤트
_.eventHandler(".login-form", "submit", (e) => userController.login(e, userViewHandler.login));
_.eventHandler(".signup-form", "submit", (e) => userController.signup(e, userViewHandler.signup.bind(userViewHandler)));
_.eventHandler(".logout-button", "click", (e) => userController.logout(e, userViewHandler.logout));

// 회원가입 유효성 체크
_.eventHandler(".signup-form", "focusout", userController.validateValue.bind(userController));

// myBoards 관련 이벤트
boardsController.domLoaded(boardsViewHandler.printBoards);
_.eventHandler(".add-board-btn", "click", boardsViewHandler.toggleModal.bind(boardsViewHandler));
_.eventHandler(".close-modal", "click", boardsViewHandler.toggleModal.bind(boardsViewHandler));
_.eventHandler(".save-board", "click", (e) => boardsController.saveBoard(boardsViewHandler.appendBoard.bind(boardsViewHandler)));

// board 관련 이벤트
boardController.domLoaded();