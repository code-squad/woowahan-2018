
const logoutButton = document.querySelector(".logout-button");

function logout(e) {
    let promise;
    e.preventDefault();
    console.log("send logout request");
    promise = new Promise((resolve) => {
        let xhr = new XMLHttpRequest();
        xhr.open("post", "/api/users/logout", true);
        xhr.addEventListener("load", () => {
        	resolve(JSON.parse(xhr.response));
        });
        xhr.setRequestHeader("Content-type", "application/json");

        xhr.send();
    });

    return promise;
}

function afterLogoutRequest(res){
    let status = res.status;

    if (status === "OK") {
        window.location.replace("/index.html");
    } else {
        console.log("logout failed.")
    }
}

logoutButton.addEventListener("click", (e) => {
    logout(e).then(afterLogoutRequest)
});
