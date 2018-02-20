import Utils from './Utils.js';

class LoginController {
    login(e) {
        e.preventDefault();

        const email = Utils.$("#email");
        const password = Utils.$("#password");

        const parameters = {
            "email": email.value,
            "password": password.value
        }

        Utils.ajax("/api/users/login", "POST", parameters).then(this.afterLoginRequest);
    }

    afterLoginRequest(res){
        let status = res.status;

        if (status === "OK") {
            window.location.replace("/boards.html");
        } else {
            console.log("asdf");
            document.querySelector(".login-notification").innerHTML = `<p> ${res.message} </p>`;
        }
    }
}

export default LoginController;
