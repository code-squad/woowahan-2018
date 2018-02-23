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

    if (dom === null) {
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


// import MESSAGE from './support/Messages.js';

const userController = new __WEBPACK_IMPORTED_MODULE_1__User_js__["a" /* default */]();


//1. 로딩(domcontentloaded 콜백에서
// 1.1 msg를 가져온다 => 전역객체에 추가.
// 1.1.2 이벤트등록(addeventlist)  <= 1.1 then callback 안에서 구현.

let MESSAGE;
document.addEventListener("DOMcontentloaded", () => {
  __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].ajax("/message.json", "GET").then((data) => {
    MESSAGE = data;
  });
})

__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].eventHandler(".login-form", "submit", userController.login.bind(userController));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].eventHandler(".signup-form", "submit", userController.signup.bind(userController));
__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].eventHandler(".logout-button", "click", userController.logout.bind(userController));

__WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].eventHandler(".signup-form", "focusout", userController.validateValue.bind(userController));



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
    };

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
    const validator = new Validator();
    const message = validator.manager(targetDom, this.showErrorMessage);
    console.log(this.showErrorMessage)
    // if (message === undefined) {
    //   targetDom.className = "validate valid";
    //   Utils.$("." + targetDom.id + "-noti").innerHTML = "";
    // } else {
    //   e.target.className = "validate invalid";
    //   Utils.$("." + targetDom.id + "-noti").innerHTML = message;
    // }
  }

  showErrorMessage(targetDom, message) {
    if (message === undefined) {
      targetDom.className = "validate valid";
      __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].$("." + targetDom.id + "-noti").innerHTML = "";
    } else {
      targetDom.className = "validate invalid";
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
      message.innerHTML = res.message;
    }
  }

  logout(res) {
    let status = res.status;

    if (status === "OK") {
      window.location.replace("/index.html");
    } else {
      console.log("logout failed.");
    }
  }
}


class Validator {
  constructor() {
    //this.MESSAGE;
  }

  manager(targetDom, showErrorMessage) {

    __WEBPACK_IMPORTED_MODULE_0__support_Utils_js__["a" /* default */].ajax("/message.json", "GET").then((data) => {
      this.MESSAGE = data;
      let message;

      switch (targetDom.id) {
        case "email":
          message = this.checkEmail(targetDom.value);
        case "password":
          message = this.checkPassword(targetDom.value);
        case "name":
          message = this.checkName(targetDom.value);
      }

      showErrorMessage(targetDom, message);
    });


  }

  checkEmail(email) {
    if (email === "") {
      return this.MESSAGE.EMAIL.EMPTY;
    } else if (email.length < 5 || email.length > 30) {
      return this.MESSAGE.EMAIL.LENGTH;
    } else if (!email.includes("@")) {
      return this.MESSAGE.EMAIL.AT;
    } else if (email[email.indexOf("@") + 1] === ".") {
      return this.MESSAGE.EMAIL.DOT_LOCATION;
    }
  }

  checkPassword(password) {
    if (password === "") {
      return this.MESSAGE.PASSWORD.EMPTY;
    } else if (password.length < 10 || password.length > 30) {
      return this.MESSAGE.PASSWORD.LENGTH;
    } else if (new RegExp("^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@#^!%*?&].*[$@#^!%*?&])[A-Za-z\\d$@#^!%*?&]{10,}").test(dom.value)) {
      return this.MESSAGE.PASSWORD.PATTERN;
    }
  }

  checkName(name) {
    if (name.length === 0) {
      return this.MESSAGE.NAME.EMPTY;
    }
  }

}

/* harmony default export */ __webpack_exports__["a"] = (UserController);

/***/ }),
/* 3 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__Utils__ = __webpack_require__(0);


const Message = {

  get() {

  }
}


/* unused harmony default export */ var _unused_webpack_default_export = (Message);


/***/ })
/******/ ]);