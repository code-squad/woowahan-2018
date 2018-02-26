import { _, API } from '../support/Utils.js';
import Validator from './Validator.js';
import UserViewHandler from './UserViewHandler.js';

class UserController {
    constructor() {
        this.validator = new Validator();
        this.userViewHandler = new UserViewHandler();
    }

    login(e) {
        e.preventDefault();
        const loginURL = API.USERS.LOGIN;
        const email = _.$("#email");
        const password = _.$("#password");
        const parameters = {
            "email": email.value,
            "password": password.value
        }

        _.ajax(loginURL, "POST", parameters).then(this.userViewHandler.login);
    }

    signup(e) {
        e.preventDefault();

        const signupURL = API.USERS.SIGNUP;
        const name = _.$("#name");
        const email = _.$("#email");
        const password = _.$("#password");

        const parameters = {
            "name": name.value,
            "email": email.value,
            "password": password.value
        };

        _.ajax(signupURL, "POST", parameters).then(this.userViewHandler.signup.bind(this.userViewHandler));
    }

    logout(e) {
        e.preventDefault();

        const logoutURL = API.USERS.LOGOUT;
        _.ajax(logoutURL, "POST").then(this.userViewHandler.logout);
    }

    validateValue(e) {
        const targetDom = e.target;
        const message = this.validator.manager(targetDom);
        this.userViewHandler.showErrorMessage(targetDom, message);
    }

}

export default UserController;