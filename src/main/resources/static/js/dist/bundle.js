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
class Utils {
    static ajax(url, httpMethod, parameters) {
        return new Promise((resolve) => {
            let xhr = new XMLHttpRequest();
            xhr.open(httpMethod, url, true);
            xhr.addEventListener("load", () => {
            	resolve(JSON.parse(xhr.response));
            });
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.send(JSON.stringify(parameters));
        });
    }

    static $(selector) {
        return document.querySelector(selector);
    }

    static eventHandler(selector, event, callback) {
        const dom = this.$(selector);

        if(dom === null) {
            return;
        }

        dom.addEventListener(event, callback);
    }
}

/* harmony default export */ __webpack_exports__["a"] = (Utils);


/***/ }),
/* 1 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__User_js__ = __webpack_require__(2);



const userController = new __WEBPACK_IMPORTED_MODULE_1__User_js__["a" /* default */]();

__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].eventHandler(".login-form", "submit", userController.login.bind(userController));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].eventHandler(".signup-form", "submit", userController.signup.bind(userController));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].eventHandler(".logout-button", "click", userController.logout.bind(userController));

__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].eventHandler(".signup-form", "focusout", userController.validateValue);

/***/ }),
/* 2 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__support_Messages_js__ = __webpack_require__(3);



class UserController {
    constructor() {
        this.userResponseHandler = new UserResponseHandler();
    }

    login(e) {
        e.preventDefault();
        const loginURL = "/api/users/login";
        const email = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$("#email");
        const password = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$("#password");
        const parameters = {
            "email": email.value,
            "password": password.value
        }

        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].ajax(loginURL, "POST", parameters).then(this.userResponseHandler.login);
    }

    signup(e) {
        e.preventDefault();

        const signupURL = "/api/users";
        const name = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$("#name");
        const email = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$("#email");
        const password = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$("#password");

        const parameters = {
            "name": name.value,
            "email": email.value,
            "password": password.value
        };

        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].ajax(signupURL, "POST", parameters).then(this.userResponseHandler.signup);
    }

    logout(e) {
        e.preventDefault();

        const logoutURL = "/api/users/logout";
        __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].ajax(logoutURL, "POST").then(this.userResponseHandler.logout);
    }

    validateValue(e) {
        const targetDom = e.target;
        const message = new Validator().manager(targetDom);

        if (message === undefined) {
            targetDom.className = "validate valid";
            __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$("." + targetDom.id + "-noti").innerHTML = "";
        } else {
            e.target.className = "validate invalid";
            __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$("." + targetDom.id + "-noti").innerHTML = message;
        }
    }

}

class UserResponseHandler {
    login(res) {
        let status = res.status;
        if (status === "OK") {
            window.location.replace("/boards.html");
        } else {
            __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$(".login-notification").innerHTML = `<p> ${res.message} </p>`;
        }
    }

    signup(res) {
        const message = __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$("#message");
        const status = res.status;

        if (status === "OK")
            window.location.href = "/login.html";
        else {
            message.innerHTML = res.message
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
}


class Validator {
    manager(targetDom) {
        switch(targetDom.id) {
            case "email":
                return this.checkEmail(targetDom.value);
            case "password":
                return this.checkPassword(targetDom.value);
            case "name":
                return this.checkName(targetDom.value);
        }
    }

    checkEmail(email) {
        if (email === "") {
            return __WEBPACK_IMPORTED_MODULE_1__support_Messages_js__["a" /* default */].EMAIL.EMPTY;
        } else if (email.length < 5 || email.length > 30) {
            return __WEBPACK_IMPORTED_MODULE_1__support_Messages_js__["a" /* default */].EMAIL.LENGTH;
        } else if (!email.includes("@")) {
            return __WEBPACK_IMPORTED_MODULE_1__support_Messages_js__["a" /* default */].EMAIL.AT;
        } else if (email[email.indexOf("@") + 1] === ".") {
            return __WEBPACK_IMPORTED_MODULE_1__support_Messages_js__["a" /* default */].EMAIL.DOT_LOCATION;
        }
    }

    checkPassword(password) {
        if (password === "") {
            return __WEBPACK_IMPORTED_MODULE_1__support_Messages_js__["a" /* default */].PASSWORD.EMPTY;
        } else if (password.length < 10 || password.length > 30) {
            return __WEBPACK_IMPORTED_MODULE_1__support_Messages_js__["a" /* default */].PASSWORD.LENGTH;
        } else if (new RegExp("^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@#^!%*?&].*[$@#^!%*?&])[A-Za-z\\d$@#^!%*?&]{10,}").test(dom.value)) {
            return __WEBPACK_IMPORTED_MODULE_1__support_Messages_js__["a" /* default */].PASSWORD.PATTERN;
        }
    }

    checkName(name) {
        if (name.length === 0) {
            return __WEBPACK_IMPORTED_MODULE_1__support_Messages_js__["a" /* default */].NAME.EMPTY;
        }
    }
}

/* harmony default export */ __webpack_exports__["a"] = (UserController);

/***/ }),
/* 3 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
const SIGNUP_MSG = {
    EMAIL: {
        EMPTY: "이메일을 입력해주세요.",
        LENGTH: "이메일은 5자 이상, 30자 이하이어야 합니다.",
        AT: "이메일은 @를 포함해야 합니다.",
        DOT_LOCATION: "'.'에서 '.'의 위치가 잘못되었습니다."
    },

    PASSWORD: {
        EMPTY: "비밀번호를 입력해주세요",
        LENGTH: "비밀번호는 10자 이상, 30자 이하이어야 합니다.",
        PATTERN: "비밀번호는 문자/숫자를 각각 1개 이상, 특수문자를 2개 이상 포함해야 합니다."
    },

    NAME: {
        EMPTY: "사용자 이름을 입력해주세요."
    }
}

/* harmony default export */ __webpack_exports__["a"] = (SIGNUP_MSG);

/***/ })
/******/ ]);