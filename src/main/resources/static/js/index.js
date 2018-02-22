import Utils from './support/Utils.js';
import UserController from './User.js';

const userController = new UserController();

Utils.eventHandler(".login-form", "submit", userController.login.bind(userController));
Utils.eventHandler(".signup-form", "submit", userController.signup.bind(userController));
Utils.eventHandler(".logout-button", "click", userController.logout.bind(userController));

Utils.eventHandler(".signup-form", "focusout", userController.validateValue);