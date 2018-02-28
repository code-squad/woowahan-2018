import { _ } from '../support/Utils.js';
import MSG from '../../message.json';

class UserViewHandler {
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
            if (!Array.isArray(res)) {
                const targetDom = _.$("#" + res.field);
                this.showErrorMessage(targetDom, res.message);
                return;
            }

            res.forEach((data) => {
                const targetDom = _.$("#" + data.field);
                this.showErrorMessage(targetDom, data.message);
            })
        }
    }

    logout(res) {
        const status = res.status;

        if (status === "OK") {
            window.location.replace("/index.html");
        } else {
            console.log("logout failed.")
        }
    }

    showErrorMessage(targetDom, message) {
        if (targetDom.classList.contains("waves-button-input")) {
            return;
        }

        if (message === undefined) {
            targetDom.className = "validate valid";
            _.$(`.${targetDom.id}-noti`).innerHTML = "";
        } else {
            targetDom.className = "validate invalid";
            _.$(`.${targetDom.id}-noti`).innerHTML = message;
        }
    }
}

export default UserViewHandler;