import Utils from './support/Utils.js';
import Message from './support/Messages.js';

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
    };

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
      Utils.$("." + targetDom.id + "-noti").innerHTML = "";
    } else {
      targetDom.className = "validate invalid";
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

    Utils.ajax("/message.json", "GET").then((data) => {
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

export default UserController;