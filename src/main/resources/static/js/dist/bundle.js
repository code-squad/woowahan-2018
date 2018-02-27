/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 1);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return _; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "c", function() { return boardUtils; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return API; });
const _ = {
    ajax(url, httpMethod, parameters) {
        return new Promise((resolve) => {
            let xhr = new XMLHttpRequest();
            xhr.open(httpMethod, url, true);
            xhr.addEventListener("load", () => {
            	resolve(JSON.parse(xhr.response));
            });
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.send(JSON.stringify(parameters));
        });
    },

    $(selector) {
        return document.querySelector(selector);
    },

    eventHandler(selector, event, callback) {
        const dom = this.$(selector);

        if(dom === null) {
            return;
        }

        dom.addEventListener(event, callback);
    }
}

const boardUtils = {
    createTemplate(html, data) {
        return html.replace(/{{(\w*)}}/g, (m, key) => {
            return data.hasOwnProperty(key) ? data[key] : "";
        })
    }
}

const API = {
    USERS: {
        SIGNUP: "/api/users",
        LOGIN: "/api/users/login",
        LOGOUT: "/api/users/logout"
    },
    BOARDS: {
        MYBOARD: "/api/boards",
        BOARD(boardId) {
            return `/api/boards/${boardId}`;
        },
        DECKS(boardId) {
            return `/api/boards/${boardId}/decks`;
        },
        CARDS(boardId, deckId) {
            return `/api/boards/${boardId}/decks/${deckId}/cards`;
        },
        ADDMEMBER(boardId) {
            return `/api/boards/${boardId}/members`;
        }
    }

}




/***/ }),
/* 1 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__user_UserController_js__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__boards_BoardsController_js__ = __webpack_require__(10);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__board_BoardController_js__ = __webpack_require__(5);





const userController = new __WEBPACK_IMPORTED_MODULE_1__user_UserController_js__["a" /* default */]();
const boardsController = new __WEBPACK_IMPORTED_MODULE_2__boards_BoardsController_js__["a" /* default */]();
const boardController = new __WEBPACK_IMPORTED_MODULE_3__board_BoardController_js__["a" /* default */]();

// user관련 이벤트
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".login-form", "submit", (e) => userController.login(e));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".signup-form", "submit", (e) => userController.signup(e));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".button-logout", "click", (e) => userController.logout(e));

// 회원가입 유효성 체크
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".signup-form", "focusout", userController.validateValue.bind(userController));

// myBoards 관련 이벤트
boardsController.domLoaded();

// board 관련 이벤트
boardController.domLoaded();

/***/ }),
/* 2 */,
/* 3 */
/***/ (function(module, exports) {

module.exports = {"EMAIL":{"EMPTY":"이메일을 입력해주세요.","LENGTH":"이메일은 5자 이상, 30자 이하이어야 합니다.","AT":"이메일은 @를 포함해야 합니다.","DOT_LOCATION":"'.'에서 '.'의 위치가 잘못되었습니다.","PATTERN":"유효한 이메일 형식이 아닙니다."},"PASSWORD":{"EMPTY":"비밀번호를 입력해주세요","LENGTH":"비밀번호는 10자 이상, 30자 이하이어야 합니다.","PATTERN":"비밀번호는 문자/숫자를 각각 1개 이상, 특수문자를 2개 이상 포함해야 합니다."},"NAME":{"EMPTY":"사용자 이름을 입력해주세요."}}

/***/ }),
/* 4 */,
/* 5 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__BoardHandler_js__ = __webpack_require__(6);



class BoardController {
    domLoaded(callback) {
        if (window.location.pathname !== "/board.html") {
            return;
        }

        document.addEventListener("DOMContentLoaded", () => {
            const params = new URLSearchParams(document.location.search.substring(1));
            const boardId = params.get("boardId");
            const boardHandler = new __WEBPACK_IMPORTED_MODULE_1__BoardHandler_js__["a" /* default */](boardId);
            this.getBoard(boardId, boardHandler.printBoard.bind(boardHandler));
        });
    }

    getBoard(boardId, callback) {
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].BOARDS.BOARD(boardId), "GET").then(callback);
    }
}

/* harmony default export */ __webpack_exports__["a"] = (BoardController);

/***/ }),
/* 6 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__DeckHandler_js__ = __webpack_require__(7);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__ = __webpack_require__(0);



class BoardHandler {
    constructor(boardId) {
        this.deckHandler = new __WEBPACK_IMPORTED_MODULE_0__DeckHandler_js__["a" /* default */](boardId);
        this.errorMessage = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".error-message");
        this.boardId = boardId;
    }

    toggleMembersForm() {
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".member-list").classList.toggle("open");
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$("#button-member-add").value = "";
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".error-member-message").innerHTML = "";
    }

    addMembers() {
        const email = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$("#button-member-add").value;
        const data = {
            "email": email
        }

        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["a" /* API */].BOARDS.ADDMEMBER(this.boardId), "POST", data).then(this.addMembersResponseHandler.bind(this));
    }

    addMembersResponseHandler(res) {
        if (res.status === "OK") {
            this.toggleMembersForm();
            this.board.members = res.content;
        } else {
            __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".error-member-message").innerHTML = res.message;
        }
    }

    printBoardName(boardName) {
        const boardNameDom = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".board-name");
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

        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".exist-member-list").innerHTML = html;
    }

    boardEventHandler() {
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].eventHandler("#button-board-members", "click", (e) => {
            this.toggleMembersForm();
            this.printMembers();
        })

        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].eventHandler(".add-member-form", "submit", (e) => {
            e.preventDefault();
            this.addMembers();
        })
    }
}

/* harmony default export */ __webpack_exports__["a"] = (BoardHandler);

/***/ }),
/* 7 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_template_js__ = __webpack_require__(15);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__CardHandler_js__ = __webpack_require__(8);




class DeckHandler {
    constructor(boardId) {
        this.cardHandler = new __WEBPACK_IMPORTED_MODULE_2__CardHandler_js__["a" /* default */](boardId);
        this.deckList = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".deck-list");
        this.errorMessage = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".error-message");
        this.boardId = boardId;
    }

    toggleDeckForm() {
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".add-deck-form").classList.toggle("open");
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$("#add-deck").value = "";
    }

    saveDeck(e, callback) {
        const nameDom = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$("#add-deck");

        const data = {
            "name": nameDom.value
        };

        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["a" /* API */].BOARDS.DECKS(this.boardId), "POST", data).then(callback);
    }

    appendDeck(res) {
        const status = res.status;

        if (status === "OK") {
            this.deckList.insertAdjacentHTML("beforeend", __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["c" /* boardUtils */].createTemplate(__WEBPACK_IMPORTED_MODULE_0__support_template_js__["a" /* default */].deck, {"id" : res.content.id, "value": res.content.name}));
            this.cardHandler.cardEventHandler(res.content.id);
            this.errorMessage.innerHTML = "";
        } else {
            this.errorMessage.innerHTML = res.message;
        }

        this.toggleDeckForm();
    }

    printDecks(decks) {
        decks.forEach((deck) => {
            this.deckList.insertAdjacentHTML("beforeend", __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["c" /* boardUtils */].createTemplate(__WEBPACK_IMPORTED_MODULE_0__support_template_js__["a" /* default */].deck, {"id" : deck.id, "value" : deck.name}));
            this.cardHandler.printCards(deck.id, deck.cards);
            this.cardHandler.cardEventHandler(deck.id);
        });
        this.errorMessage.innerHTML = "";
    }

    deckEventHandler() {
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].eventHandler(".add-deck-area", "click", (e, callback) => {
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

/* harmony default export */ __webpack_exports__["a"] = (DeckHandler);

/***/ }),
/* 8 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_template_js__ = __webpack_require__(15);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__ = __webpack_require__(0);



class CardHandler {
    constructor(boardId) {
        this.boardId = boardId;
    }

    toggleCardForm(id) {
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(`#add-card-form-${id}`).classList.toggle("open");
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(`#add-card-btn-${id}`).classList.toggle("close");
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(`#card-title-${id}`).value = "";
    }

    saveCard(deckId, callback) {
        const data = {
            "text": __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(`#card-title-${deckId}`).value
        };

        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["a" /* API */].BOARDS.CARDS(this.boardId, deckId), "POST", data).then(callback);
    }

    appendCard(res) {
        const status = res.status;
        const deckId = res.content.deckId;
        const card = res.content.card
        const errorMessage = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(".error-message");

        if (status === "OK") {
            __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(`#deck-cards-${deckId}`).insertAdjacentHTML("beforeend", __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["c" /* boardUtils */].createTemplate(__WEBPACK_IMPORTED_MODULE_0__support_template_js__["a" /* default */].card, {"value": card.text}));
        } else {
            errorMessage.innerHTML = res.message;
        }
    }

    printCards(deckId, cards) {
        const html = cards.reduce((html, card) => {
            return html + __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["c" /* boardUtils */].createTemplate(__WEBPACK_IMPORTED_MODULE_0__support_template_js__["a" /* default */].card, {"value" : card.text})
        }, "")

        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$(`#deck-cards-${deckId}`).insertAdjacentHTML("beforeend", html);
    }

    cardEventHandler(deckId) {
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].eventHandler(".deck-list", "click", (e) => {
            if (e.target.id === "add-card-btn-" + deckId) {
                this.toggleCardForm(deckId);
            } else if (e.target.id === "cancel-card-" + deckId) {
                this.toggleCardForm(deckId);
            } else if (e.target.id === "save-card-" + deckId) {
                this.saveCard(deckId, this.appendCard);
                this.toggleCardForm(deckId);
            }
        })
    }
}

/* harmony default export */ __webpack_exports__["a"] = (CardHandler);

/***/ }),
/* 9 */,
/* 10 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__BoardsHandler_js__ = __webpack_require__(11);



class BoardsController {
    constructor() {
        this.boardsHandler = new __WEBPACK_IMPORTED_MODULE_1__BoardsHandler_js__["a" /* default */]();
    }

    domLoaded() {
        if (window.location.pathname !== "/boards.html") {
            return;
        }

        document.addEventListener("DOMcontentLoaded", this.getBoards());
    }

    getBoards() {
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].BOARDS.MYBOARD, "GET").then(this.boardsHandler.printBoards.bind(this.boardsHandler));
    }

}

/* harmony default export */ __webpack_exports__["a"] = (BoardsController);

/***/ }),
/* 11 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_template_js__ = __webpack_require__(15);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__ = __webpack_require__(0);



class BoardsHandler {
    constructor() {
        this.modalDiv = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$('#modal');
    }

    toggleModal() {
        this.modalDiv.classList.toggle('open');
    }

    saveBoard() {
        const nameDom = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$('.board-name');
        const parameters = {
            "name": nameDom.value
        }

        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["a" /* API */].BOARDS.MYBOARD, "POST", parameters).then(this.appendBoard.bind(this));
    }

    printBoards(res) {
        const boards = res.content.boards;
        const boardListDom = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$('.board-list');

        boards.forEach((item) => {
            boardListDom.innerHTML += __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["c" /* boardUtils */].createTemplate(__WEBPACK_IMPORTED_MODULE_0__support_template_js__["a" /* default */].board, {'id': item.id, 'name': item.name});
        })

        this.boardsEventHandler();
    }

    appendBoard(res) {
        const status = res.status;
        const nameDom = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$('.board-name');
        const boardListDom = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$('.board-list');

        if (status === "OK") {
            boardListDom.insertAdjacentHTML('beforeend', __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["c" /* boardUtils */].createTemplate(__WEBPACK_IMPORTED_MODULE_0__support_template_js__["a" /* default */].board, {'id' : res.content.id, 'name': res.content.name}));
            this.toggleModal();
            nameDom.value = "";
        } else {
            const warning = __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].$('.warning');
            warning.innerHTML = res.message;
            warning.style.display = 'block';
        }
    }

    boardsEventHandler() {
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].eventHandler(".add-board-btn", "click", this.toggleModal.bind(this));
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].eventHandler(".close-modal", "click", this.toggleModal.bind(this));
        __WEBPACK_IMPORTED_MODULE_1__support_Utils_js__["b" /* _ */].eventHandler(".save-board", "click", this.saveBoard.bind(this));
    }

}

/* harmony default export */ __webpack_exports__["a"] = (BoardsHandler);

/***/ }),
/* 12 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__Validator_js__ = __webpack_require__(13);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__UserViewHandler_js__ = __webpack_require__(14);




class UserController {
    constructor() {
        this.validator = new __WEBPACK_IMPORTED_MODULE_1__Validator_js__["a" /* default */]();
        this.userViewHandler = new __WEBPACK_IMPORTED_MODULE_2__UserViewHandler_js__["a" /* default */]();
    }

    login(e) {
        e.preventDefault();
        const loginURL = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].USERS.LOGIN;
        const email = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#email");
        const password = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#password");
        const parameters = {
            "email": email.value,
            "password": password.value
        }

        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(loginURL, "POST", parameters).then(this.userViewHandler.login);
    }

    signup(e) {
        e.preventDefault();

        const signupURL = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].USERS.SIGNUP;
        const name = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#name");
        const email = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#email");
        const password = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#password");

        const parameters = {
            "name": name.value,
            "email": email.value,
            "password": password.value
        };

        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(signupURL, "POST", parameters).then(this.userViewHandler.signup.bind(this.userViewHandler));
    }

    logout(e) {
        e.preventDefault();

        const logoutURL = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].USERS.LOGOUT;
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(logoutURL, "POST").then(this.userViewHandler.logout);
    }

    validateValue(e) {
        const targetDom = e.target;
        const message = this.validator.manager(targetDom);
        this.userViewHandler.showErrorMessage(targetDom, message);
    }

}

/* harmony default export */ __webpack_exports__["a"] = (UserController);

/***/ }),
/* 13 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__message_json__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__message_json___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__message_json__);


class Validator {
    manager(targetDom) {
        const checkValue = {
            'email': this.checkEmail.bind(this),
            'password': this.checkPassword.bind(this),
            'name': this.checkName.bind(this)
        };

        if (checkValue[targetDom.id]) {
            return checkValue[targetDom.id](targetDom.value);
        }

    }

    checkEmail(email) {
        if (email === "") {
            return __WEBPACK_IMPORTED_MODULE_0__message_json__["EMAIL"].EMPTY;
        } else if (email.length < 5 || email.length > 30) {
            return __WEBPACK_IMPORTED_MODULE_0__message_json__["EMAIL"].LENGTH;
        } else if (!email.includes("@")) {
            return __WEBPACK_IMPORTED_MODULE_0__message_json__["EMAIL"].AT;
        } else if (email[email.indexOf("@") + 1] === ".") {
            return __WEBPACK_IMPORTED_MODULE_0__message_json__["EMAIL"].DOT_LOCATION;
        }
    }

    checkPassword(password) {
        if (password === "") {
            return __WEBPACK_IMPORTED_MODULE_0__message_json__["PASSWORD"].EMPTY;
        } else if (password.length < 10 || password.length > 30) {
            return __WEBPACK_IMPORTED_MODULE_0__message_json__["PASSWORD"].LENGTH;
        } else if (!new RegExp("^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@#^!%*?&].*[$@#^!%*?&])[A-Za-z\\d$@#^!%*?&]{10,}").test(password)) {
            return __WEBPACK_IMPORTED_MODULE_0__message_json__["PASSWORD"].PATTERN;
        }
    }

    checkName(name) {
        if (name.length === 0) {
            return __WEBPACK_IMPORTED_MODULE_0__message_json__["NAME"].EMPTY;
        }
    }
}

/* harmony default export */ __webpack_exports__["a"] = (Validator);

/***/ }),
/* 14 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);


class UserViewHandler {
    login(res) {
        let status = res.status;
        if (status === "OK") {
            window.location.replace("/boards.html");
        } else {
            __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(".login-notification").innerHTML = `<p> ${res.message} </p>`;
        }
    }

    signup(res) {
        const message = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#message");
        const status = res.status;

        if (status === "OK")
            window.location.href = "/login.html";
        else {
            if (!Array.isArray(res)) {
                const targetDom = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#" + res.field);
                this.showErrorMessage(targetDom, res.message);
                return;
            }

            res.forEach((data) => {
                const targetDom = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#" + data.field);
                this.showErrorMessage(targetDom, data.message);
            })
        }
    }

    logout(res) {
        const status = res.status;

        if (status === "OK") {
            window.location.replace("/index.html");
        } else {
            console.log("logout failed.")
        }
    }

    showErrorMessage(targetDom, message) {
        if (message === undefined) {
            targetDom.className = "validate valid";
            __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("." + targetDom.id + "-noti").innerHTML = "";
        } else {
            targetDom.className = "validate invalid";
            __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("." + targetDom.id + "-noti").innerHTML = message;
        }
    }
}

/* harmony default export */ __webpack_exports__["a"] = (UserViewHandler);

/***/ }),
/* 15 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var Template = {

  board : "<a class='board waves-effect waves-light btn' href='../board.html?boardId={{id}}'>" +
                    "{{name}}" +
          "</a>",

  deck : "<div class='deck-wrapper'>" +
                "<div class='deck-content z-depth-1'>" +
                    "<div class='deck-header'>" +
                      "<textarea class='deck-header-name'>{{value}}</textarea>" +
                    "</div>" +
                    "<div class='deck-cards' id='deck-cards-{{id}}'></div>" +
                    "<div class='card-composer'>" +
                       "<div class='add-card-form' id='add-card-form-{{id}}'>" +
                          "<textarea class='card-title' id='card-title-{{id}}'></textarea>" +
                          "<div class='btn-area'>" +
                            "<button class='btn waves-effect waves-light save-card' id='save-card-{{id}}'>save</button>" +
                            "<button class='btn waves-effect waves-light cancel-card' id='cancel-card-{{id}}'>cancel</button>" +
                          "</div>" +
                       "</div>" +
                       "<a class='add-card-btn' id='add-card-btn-{{id}}' href='#'>Add a Card...</a>" +
                    "</div>" +
                "</div>" +
              "</div>",
  card : "<div class='deck-card'>" +
  						"<div class='deck-card-detail'>" +
                  "<a class='deck-card-title modal-trigger modalLink' dir='auto' href='#'>{{value}}</a>" +
              "</div>" +
          "</div>",

  comment :  "<div class='comment'>" +
                "<div class='commenter'>{writer-name}</div>" +
                "<div class='comment-contents z-depth-1'>{{comment-contents}}</div>" +
                "<div class='comment-date'>{{current-time}} - </div>" +
                "<div class='comment-reply'> Reply</div>" +
    			  "</div>"

};

/* harmony default export */ __webpack_exports__["a"] = (Template);

/***/ })
/******/ ]);