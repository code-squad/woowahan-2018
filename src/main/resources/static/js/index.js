import { _ } from './support/Utils.js';
import UserController from './user/UserController.js';
import BoardsController from './boards/BoardsController.js';
import BoardController from './board/BoardController.js';

const userController = new UserController();
const boardsController = new BoardsController();
const boardController = new BoardController();

// user관련 이벤트
_.eventHandler(".login-form", "submit", (e) => userController.login(e));
_.eventHandler(".signup-form", "submit", (e) => userController.signup(e));
_.eventHandler(".button-logout", "click", (e) => userController.logout(e));

// 회원가입 유효성 체크
_.eventHandler(".signup-form", "focusout", userController.validateValue.bind(userController));

// myBoards 관련 이벤트
boardsController.domLoaded();

// board 관련 이벤트
boardController.domLoaded();