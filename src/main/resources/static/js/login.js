console.log("login.js");

const form = document.querySelector("form");
const email = document.querySelector("#email");
const password = document.querySelector("#password");

function login(e) {
    let promise;
    e.preventDefault();
    console.log("send login request");
    promise = new Promise((resolve) => {
        let xhr = new XMLHttpRequest();
        xhr.open("post", "/api/users/login", true);
        xhr.addEventListener("load", () => {
        	resolve(JSON.parse(xhr.response));
        });
        xhr.setRequestHeader("Content-type", "application/json");

        const data = {
            "email" : email.value,
            "password" : password.value
        };

        xhr.send(JSON.stringify(data));
    });

    return promise;
}

function afterLoginRequest(res){
    let status = res.status;

    if (status === "OK") {
        window.location.replace("/boards.html");
    } else {
        document.querySelector(".login-notification").innerHTML = `<p> ${res.message} </p>`;
    }
}

form.addEventListener("submit", (e) => {
    login(e).then(afterLoginRequest)
});
