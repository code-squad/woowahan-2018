import Utils from './support/Utils.js';
import SIGNUP_MSG from './support/Messages.js';

class UserController {
    constructor() {
        this.userResponseHandler = new UserResponseHandler();
    }

    login(e) {
        e.preventDefault();
        const loginURL = "/api/users/login";
        const email = Utils.$("#email");
        const password = Utils.$("#password");
        const parameters = {
            "email": email.value,
            "password": password.value
        }

        Utils.ajax(loginURL, "POST", parameters).then(this.userResponseHandler.login);
    }

    signup(e) {
        e.preventDefault();

        const signupURL = "/api/users";
        const name = Utils.$("#name");
        const email = Utils.$("#email");
        const password = Utils.$("#password");

        const parameters = {
            "name": name.value,
            "email": email.value,
            "password": password.value
        };

        Utils.ajax(signupURL, "POST", parameters).then(this.userResponseHandler.signup);
    }

    logout(e) {
        e.preventDefault();

        const logoutURL = "/api/users/logout";
        Utils.ajax(logoutURL, "POST").then(this.userResponseHandler.logout);
    }

    validateValue(e) {
        const targetDom = e.target;
        const message = new Validator().manager(targetDom);

        if (message === undefined) {
            targetDom.className = "validate valid";
            Utils.$("." + targetDom.id + "-noti").innerHTML = "";
        } else {
            e.target.className = "validate invalid";
            Utils.$("." + targetDom.id + "-noti").innerHTML = message;
        }
    }

}

class UserResponseHandler {
    login(res) {
        let status = res.status;
        if (status === "OK") {
            window.location.replace("/boards.html");
        } else {
            Utils.$(".login-notification").innerHTML = `<p> ${res.message} </p>`;
        }
    }

    signup(res) {
        const message = Utils.$("#message");
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
        } else if (new RegExp("^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@#^!%*?&].*[$@#^!%*?&])[A-Za-z\\d$@#^!%*?&]{10,}").test(dom.value)) {
            return SIGNUP_MSG.PASSWORD.PATTERN;
        }
    }

    checkName(name) {
        if (name.length === 0) {
            return SIGNUP_MSG.NAME.EMPTY;
        }
    }
}

export default UserController;