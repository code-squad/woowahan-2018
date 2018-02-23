import { _ } from './support/Utils.js';
import * as SIGNUP_MSG from '../message.json'

class UserController {
    constructor() {
        this.validator = new Validator();
    }

    login(e, callback) {
        e.preventDefault();
        const loginURL = "/api/users/login";
        const email = _.$("#email");
        const password = _.$("#password");
        const parameters = {
            "email": email.value,
            "password": password.value
        }

        _.ajax(loginURL, "POST", parameters).then(callback);
    }

    signup(e, callback) {
        e.preventDefault();

        const signupURL = "/api/users";
        const name = _.$("#name");
        const email = _.$("#email");
        const password = _.$("#password");

        const parameters = {
            "name": name.value,
            "email": email.value,
            "password": password.value
        };

        _.ajax(signupURL, "POST", parameters).then(callback);
    }

    logout(e, callback) {
        e.preventDefault();

        const logoutURL = "/api/users/logout";
        _.ajax(logoutURL, "POST").then(callback);
    }

    validateValue(e) {
        const targetDom = e.target;
        const message = this.validator.manager(targetDom);

        if (message === undefined) {
            targetDom.className = "validate valid";
            _.$("." + targetDom.id + "-noti").innerHTML = "";
        } else {
            e.target.className = "validate invalid";
            _.$("." + targetDom.id + "-noti").innerHTML = message;
        }

    }

}

class UserResponseHandler {
    login(res) {
        let status = res.status;
        if (status === "OK") {
            window.location.replace("/boards.html");
        } else {
            _.$(".login-notification").innerHTML = `<p> ${res.message} </p>`;
        }
    }

    signup(res) {
        const message = _.$("#message");
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
            return SIGNUP_MSG.EMAIL.EMPTY;
        } else if (email.length < 5 || email.length > 30) {
            return SIGNUP_MSG.EMAIL.LENGTH;
        } else if (!email.includes("@")) {
            return SIGNUP_MSG.EMAIL.AT;
        } else if (email[email.indexOf("@") + 1] === ".") {
            return SIGNUP_MSG.EMAIL.DOT_LOCATION;
        }
    }

    checkPassword(password) {
        if (password === "") {
            return SIGNUP_MSG.PASSWORD.EMPTY;
        } else if (password.length < 10 || password.length > 30) {
            return SIGNUP_MSG.PASSWORD.LENGTH;
        } else if (!new RegExp("^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@#^!%*?&].*[$@#^!%*?&])[A-Za-z\\d$@#^!%*?&]{10,}").test(password)) {
            return SIGNUP_MSG.PASSWORD.PATTERN;
        }
    }

    checkName(name) {
        if (name.length === 0) {
            return SIGNUP_MSG.NAME.EMPTY;
        }
    }
}

export { UserController, UserResponseHandler };