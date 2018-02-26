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
        CARDS(deckId) {
            return `/api/decks/${deckId}/cards`;
        }
    }

}




/***/ }),
/* 1 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__User_js__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__boards_js__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__board_js__ = __webpack_require__(5);





const userController = new __WEBPACK_IMPORTED_MODULE_1__User_js__["a" /* UserController */]();
const userViewHandler = new __WEBPACK_IMPORTED_MODULE_1__User_js__["b" /* UserViewHandler */]();
const boardsController = new __WEBPACK_IMPORTED_MODULE_2__boards_js__["a" /* BoardsController */]();
const boardsViewHandler = new __WEBPACK_IMPORTED_MODULE_2__boards_js__["b" /* BoardsViewHandler */]();
const boardController = new __WEBPACK_IMPORTED_MODULE_3__board_js__["a" /* BoardController */]();
const boardViewHandler = new __WEBPACK_IMPORTED_MODULE_3__board_js__["b" /* BoardViewHandler */]();

// user관련 이벤트
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".login-form", "submit", (e) => userController.login(e, userViewHandler.login));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".signup-form", "submit", (e) => userController.signup(e, userViewHandler.signup.bind(userViewHandler)));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".logout-button", "click", (e) => userController.logout(e, userViewHandler.logout));

// 회원가입 유효성 체크
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".signup-form", "focusout", userController.validateValue.bind(userController));

// myBoards 관련 이벤트
boardsController.domLoaded(boardsViewHandler.printBoards);
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".add-board-btn", "click", boardsViewHandler.toggleModal.bind(boardsViewHandler));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".close-modal", "click", boardsViewHandler.toggleModal.bind(boardsViewHandler));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".save-board", "click", (e) => boardsController.saveBoard(boardsViewHandler.appendBoard.bind(boardsViewHandler)));

// board 관련 이벤트
boardController.domLoaded(boardViewHandler.printBoard.bind(boardViewHandler));

/***/ }),
/* 2 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UserController; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return UserViewHandler; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__message_json__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__message_json___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__message_json__);



class UserController {
    constructor() {
        this.validator = new Validator();
        this.userViewHandler = new UserViewHandler();
    }

    login(e, callback) {
        e.preventDefault();
        const loginURL = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].USERS.LOGIN;
        const email = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#email");
        const password = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#password");
        const parameters = {
            "email": email.value,
            "password": password.value
        }

        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(loginURL, "POST", parameters).then(callback);
    }

    signup(e, callback) {
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

        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(signupURL, "POST", parameters).then(callback);
    }

    logout(e, callback) {
        e.preventDefault();

        const logoutURL = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].USERS.LOGOUT;
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(logoutURL, "POST").then(callback);
    }

    validateValue(e) {
        const targetDom = e.target;
        const message = this.validator.manager(targetDom);
        this.userViewHandler.showErrorMessage(targetDom, message);

    }

}

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
            res.forEach((data) => {
                const targetDom = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#" + data.field);
                this.showErrorMessage(targetDom, data.message);
            })
        }
    }

    logout(res) {
        let status = res.status;

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
            return __WEBPACK_IMPORTED_MODULE_1__message_json__["EMAIL"].EMPTY;
        } else if (email.length < 5 || email.length > 30) {
            return __WEBPACK_IMPORTED_MODULE_1__message_json__["EMAIL"].LENGTH;
        } else if (!email.includes("@")) {
            return __WEBPACK_IMPORTED_MODULE_1__message_json__["EMAIL"].AT;
        } else if (email[email.indexOf("@") + 1] === ".") {
            return __WEBPACK_IMPORTED_MODULE_1__message_json__["EMAIL"].DOT_LOCATION;
        }
    }

    checkPassword(password) {
        if (password === "") {
            return __WEBPACK_IMPORTED_MODULE_1__message_json__["PASSWORD"].EMPTY;
        } else if (password.length < 10 || password.length > 30) {
            return __WEBPACK_IMPORTED_MODULE_1__message_json__["PASSWORD"].LENGTH;
        } else if (!new RegExp("^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@#^!%*?&].*[$@#^!%*?&])[A-Za-z\\d$@#^!%*?&]{10,}").test(password)) {
            return __WEBPACK_IMPORTED_MODULE_1__message_json__["PASSWORD"].PATTERN;
        }
    }

    checkName(name) {
        if (name.length === 0) {
            return __WEBPACK_IMPORTED_MODULE_1__message_json__["NAME"].EMPTY;
        }
    }
}



/***/ }),
/* 3 */
/***/ (function(module, exports) {

module.exports = {"EMAIL":{"EMPTY":"이메일을 입력해주세요.","LENGTH":"이메일은 5자 이상, 30자 이하이어야 합니다.","AT":"이메일은 @를 포함해야 합니다.","DOT_LOCATION":"'.'에서 '.'의 위치가 잘못되었습니다.","PATTERN":"유효한 이메일 형식이 아닙니다."},"PASSWORD":{"EMPTY":"비밀번호를 입력해주세요","LENGTH":"비밀번호는 10자 이상, 30자 이하이어야 합니다.","PATTERN":"비밀번호는 문자/숫자를 각각 1개 이상, 특수문자를 2개 이상 포함해야 합니다."},"NAME":{"EMPTY":"사용자 이름을 입력해주세요."}}

/***/ }),
/* 4 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BoardsController; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return BoardsViewHandler; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);


class BoardsController {
    domLoaded(callback) {
        if (window.location.pathname !== "/boards.html") {
            return;
        }

        document.addEventListener("DOMcontentLoaded", this.getBoards(callback))
    }

    getBoards(callback) {
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].BOARDS.MYBOARD, "GET").then(callback);
    }

    saveBoard(callback) {
        const nameDom = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$('.board-name');
        const parameters = {
            "name": nameDom.value
        }

        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].BOARDS.MYBOARD, "POST", parameters).then(callback);
    }

}

class BoardsViewHandler {
    constructor() {
        this.modalDiv = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$('#modal');
    }

    toggleModal() {
        this.modalDiv.classList.toggle('open');
    }

    printBoards(res) {
        const boards = res.content.boards;
        const boardListDom = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$('.board-list');
        boards.forEach((item) => {
            boardListDom.innerHTML += __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["c" /* boardUtils */].createTemplate(Template.board, {'id': item.id, 'name': item.name});
        })
    }

    appendBoard(res) {
        const status = res.status;
        const nameDom = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$('.board-name');
        const boardListDom = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$('.board-list');

        if (status === "OK") {
            boardListDom.insertAdjacentHTML('beforeend', __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["c" /* boardUtils */].createTemplate(Template.board, {'id' : res.content.id, 'name': res.content.name}));
            this.toggleModal();
            nameDom.value = "";
        } else {
            const warning = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$('.warning');
            warning.innerHTML = res.message;
            warning.style.display = 'block';
        }
    }

}




/***/ }),
/* 5 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BoardController; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return BoardViewHandler; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);


const params = new URLSearchParams(document.location.search.substring(1));
const boardId = params.get("boardId");

class BoardController{
    domLoaded(callback) {
        if (window.location.pathname !== "/board.html") {
            return;
        }

        document.addEventListener("DOMContentLoaded", () => {
            this.getBoard(callback);
        });
    }

    getBoard(callback) {
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].BOARDS.BOARD(boardId), "GET").then(callback);
    }
}

class BoardViewHandler {
    constructor() {
        this.deckViewHandler = new DeckViewHandler();
        this.errorMessage = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(".error-message");
    }

    printBoardName(boardName) {
        const boardNameDom = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(".board-name");
        boardNameDom.innerHTML = boardName;
        this.errorMessage.innerHTML = "";
    }

    printBoard(res) {
        if(res.status === "OK") {
            const board = res.content;
            this.printBoardName(board.name);
            this.deckViewHandler.printDecks(board.decks);
            this.deckViewHandler.deckEventHandler();
        } else {
            this.errorMessage.innerHTML = res.message;
        }
    }

}

class DeckViewHandler {
    constructor() {
        this.cardViewHandler = new CardViewHandler();
        this.deckList = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(".deck-list");
        this.errorMessage = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(".error-message");
    }

    toggleDeckForm() {
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(".add-deck-form").classList.toggle("open");
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#add-deck").value = "";
    }

    saveDeck(e, callback) {
        const nameDom = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$("#add-deck");

        const data = {
            "name": nameDom.value
        };

        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].BOARDS.DECKS(boardId), "POST", data).then(callback);
    }

    appendDeck(res) {
        const status = res.status;

        if (status === "OK") {
            this.deckList.insertAdjacentHTML("beforeend", __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["c" /* boardUtils */].createTemplate(Template.deck, {"id" : res.content.id, "value": res.content.name}));
            this.cardViewHandler.cardEventHandler(res.content.id);
            this.errorMessage.innerHTML = "";
        } else {
            this.errorMessage.innerHTML = res.message;
        }

        this.toggleDeckForm();
    }

    printDecks(decks) {
        decks.forEach((deck) => {
            this.deckList.insertAdjacentHTML("beforeend", __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["c" /* boardUtils */].createTemplate(Template.deck, {"id" : deck.id, "value" : deck.name}));
            this.cardViewHandler.printCards(deck.id, deck.cards);
            this.cardViewHandler.cardEventHandler(deck.id);
        });
        this.errorMessage.innerHTML = "";
    }

    deckEventHandler() {
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".add-deck-area", "click", (e, callback) => {
            e.preventDefault();

            if (e.target.id === "save-deck-btn") {
                this.saveDeck(e, this.appendDeck.bind(this))
            } else if (e.target.id === "add-deck-btn") {
                this.toggleDeckForm();
            } else if (e.target.id === "cancel-deck-btn") {
                this.toggleDeckForm();
            }
        })
    }
}

class CardViewHandler {
    toggleCardForm(id) {
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(`#add-card-form-${id}`).classList.toggle("open");
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(`#add-card-btn-${id}`).classList.toggle("close");
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(`#card-title-${id}`).value = "";
    }

    saveCard(deckId, callback) {
        const data = {
            "text": document.getElementById(`card-title-${deckId}`).value
        };

        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].ajax(__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* API */].BOARDS.CARDS(deckId), "POST", data).then(callback);
    }

    appendCard(res) {
        const status = res.status;
        const deckId = res.content.deckId;
        const card = res.content.card
        const errorMessage = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(".error-message");

        if (status === "OK") {
            __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(`#deck-cards-${deckId}`).insertAdjacentHTML("beforeend", __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["c" /* boardUtils */].createTemplate(Template.card, {"value": card.text}));
        } else {
            errorMessage.innerHTML = res.message;
        }
    }

    printCards(deckId, cards) {
        cards.forEach((card) => {
            __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].$(`#deck-cards-${deckId}`).insertAdjacentHTML("beforeend", __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["c" /* boardUtils */].createTemplate(Template.card, {"value" : card.text}));
        });
    }

    cardEventHandler(deckId) {
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["b" /* _ */].eventHandler(".deck-list", "click", (e) => {
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



/***/ })
/******/ ]);