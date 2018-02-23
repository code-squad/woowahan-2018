import Utils from './support/Utils.js';
import UserController from './User.js';
// import MESSAGE from './support/Messages.js';

const userController = new UserController();


//1. 로딩(domcontentloaded 콜백에서
// 1.1 msg를 가져온다 => 전역객체에 추가.
// 1.1.2 이벤트등록(addeventlist)  <= 1.1 then callback 안에서 구현.

let MESSAGE;
document.addEventListener("DOMcontentloaded", () => {
  Utils.ajax("/message.json", "GET").then((data) => {
    MESSAGE = data;
  });
})

Utils.eventHandler(".login-form", "submit", userController.login.bind(userController));
Utils.eventHandler(".signup-form", "submit", userController.signup.bind(userController));
Utils.eventHandler(".logout-button", "click", userController.logout.bind(userController));

Utils.eventHandler(".signup-form", "focusout", userController.validateValue.bind(userController));

