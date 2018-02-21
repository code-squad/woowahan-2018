const params = window.location.search.substr(1);
const boardId = params.split("=")[1];
const addDeckButton = document.querySelector(".add-deck-btn");
const addDeckForm = document.querySelector(".add-deck-form");
const addDeckArea = document.querySelector(".add-deck-area");
const closeDeckButton = document.querySelector(".cancel-deck");
const nameDom = document.querySelector("#add-deck");
const saveButton = document.querySelector(".save-deck");
const deckList = document.querySelector(".deck-list");
const boardUsername = document.querySelector(".board-username");
const errorMessasge = document.querySelector(".error-message");

function openDeckForm() {
    addDeckForm.classList.add("open");
}

function closeDeckForm() {
    addDeckForm.classList.remove("open");
}

function saveDeck() {
    let promise;
    promise = new Promise((resolve, timeLimit) => {
        let xhr = new XMLHttpRequest();
        xhr.open("post", `/api/boards/${boardId}/decks`, true);
        xhr.addEventListener("load", (e) => {
            console.log(xhr);
            resolve(JSON.parse(xhr.response));
        });
        xhr.setRequestHeader("Content-type", "application/json");
        const data = JSON.stringify(
        {
            "name": nameDom.value
        });

        xhr.send(data);
    });

    return promise;
}

function appendDeck(res) {
    let status = res.status;
    console.log(res);

    if (status === "OK") {
        deckList.innerHTML += createTemplate(Template.deck, {'value': res.content.name})
        closeDeckForm();
        errorMessasge.innerHTML = "";
    } else {
        errorMessasge.innerHTML = res.message;
    }
}

function createTemplate(html, data) {
    return html.replace(/{{(\w*)}}/g, function (m, key) {
        return data.hasOwnProperty(key) ? data[key] : "";
    });
}

function getExistDecks() {
    return new Promise((resolve, timeLimit) => {
        let xhr = new XMLHttpRequest();
        xhr.open("get", `/api/boards/${boardId}/decks`, true);
        xhr.addEventListener("load", (e) => {
            console.log(xhr);
            resolve(JSON.parse(xhr.response));
        });
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.send();
    });
}

function printAllDeck(res) {
    if(res.status == "OK") {
        const decks = res.content;
        decks.forEach((item) => {
            console.log(item)
            deckList.innerHTML += createTemplate(Template.deck, {"value" : item.name});
        });
        errorMessasge.innerHTML = "";
    } else {
        errorMessasge.innerHTML = res.message;
    }
}

function getBoardName() {
    return new Promise((resolve, timeLimit) => {
        let xhr = new XMLHttpRequest();
        xhr.open("get", `/api/boards/${boardId}`, true);
        xhr.addEventListener("load", (e) => {
            console.log(xhr);
            resolve(JSON.parse(xhr.response));
        });
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.send();
    });
}

function printBoardName(res) {
    console.log(res)
    if(res.status == "OK") {
        const boardName = res.content.name;
        boardUsername.innerHTML = boardName
        errorMessasge.innerHTML = "";
    } else {
        errorMessasge.innerHTML = res.message;
    }
}

function initControls() {
    addDeckButton.addEventListener("click", openDeckForm);
    closeDeckButton.addEventListener("click", (e) => {
        e.preventDefault();
        closeDeckForm();
    });

    saveButton.addEventListener("click", (e) => {
        e.preventDefault();
        closeDeckForm();
        saveDeck().then(appendDeck);
    });

    document.addEventListener("DOMContentLoaded", () => {
        getBoardName().then(printBoardName);
        getExistDecks().then(printAllDeck);
    });
}

initControls();
