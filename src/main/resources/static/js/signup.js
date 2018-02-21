const form = document.querySelector(".signup-form");
const name = document.querySelector("#name");
const email = document.querySelector("#email");
const password = document.querySelector("#password");
const message = document.querySelector("#message")

function signup(e) {
    var promise;
    e.preventDefault();

    promise = new Promise((resolve, timeLimit) => {
        var xhr = new XMLHttpRequest();
        xhr.open("post", "/api/users", true);
        xhr.addEventListener("load", (e) => {
            console.log(xhr)
            resolve(JSON.parse(xhr.response));
        });
        xhr.setRequestHeader("Content-type", "application/json");
        var data = JSON.stringify(
        {
            "name": name.value,
             "email" : email.value,
             "password" : password.value
        });

        xhr.send(data);
    });

    return promise;
}

function redirector(res){
    let status = res.status

    if (status === "OK")
        window.location.href = "/index.html";
    else {
        message.innerHTML = res.message
    }
    console.log(status)

}

form.addEventListener("submit", function(e) {
    signup(e)
        .then(redirector)
});