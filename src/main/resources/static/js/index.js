import LoginController from './login.js';
import Utils from './Utils.js';

const loginController = new LoginController();

Utils.$("form").addEventListener("submit", (e) => {
    loginController.login(e)
});
