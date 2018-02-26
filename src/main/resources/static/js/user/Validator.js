import * as SIGNUP_MSG from '../../message.json'

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

export default Validator;