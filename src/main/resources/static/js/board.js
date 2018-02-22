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
const errorMessage = document.querySelector(".error-message");

function openDeckForm() {
    addDeckForm.classList.add("open");
}

function closeDeckForm() {
    addDeckForm.classList.remove("open");
    nameDom.value = "";
}

function openCardForm(id) {
    document.getElementById(`add-card-form-${id}`).classList.add("open");
    document.getElementById(`add-card-btn-${id}`).classList.add("close");
}

function closeCardForm(id) {
    document.getElementById(`add-card-form-${id}`).classList.remove("open");
    document.getElementById(`add-card-btn-${id}`).classList.remove("close");
    document.getElementById(`card-title-${id}`).value = "";
}

function saveDeck() {
    return new Promise((resolve, timeLimit) => {
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
}

function saveCard(deckId) {
    return new Promise((resolve, timeLimit) => {
        let xhr = new XMLHttpRequest();
        xhr.open("post", `/api/decks/${deckId}/cards`, true);
        xhr.addEventListener("load", (e) => {
            console.log(xhr);
            resolve([deckId, JSON.parse(xhr.response)]);
        });
        xhr.setRequestHeader("Content-type", "application/json");
        const data = JSON.stringify(
        {
            "text": document.getElementById(`card-title-${deckId}`).value
        });
        xhr.send(data);
    });
}

function appendCard(res) {
    const deckId = res[0]
    res = res[1]
    let status = res.status;

    if (status === "OK") {
        document
            .getElementById(`deck-cards-${deckId}`)
            .insertAdjacentHTML("beforeend", createTemplate(Template.card, {"value" : res.content.text}));
    } else {
        errorMessage.innerHTML = res.message;
    }
}

function appendDeck(res) {
    let status = res.status;
    console.log(res);

    if (status === "OK") {
        deckList.insertAdjacentHTML("beforeend",
                                    createTemplate(Template.deck, {"id" : res.content.id, "value": res.content.name}));
        initCardButtons(res.content.id);
        closeDeckForm();
        errorMessage.innerHTML = "";
    } else {
        errorMessage.innerHTML = res.message;
    }
}

function addCardButtonListener(deckId) {
    openCardForm(deckId);
}


function createTemplate(html, data) {
    return html.replace(/{{(\w*)}}/g, function (m, key) {
        return data.hasOwnProperty(key) ? data[key] : "";
    });
}

function printBoard(res) {
    if(res.status === "OK") {
        board = res.content;
        printBoardName(board.title);
        printAllDeck(board.decks);
    } else {
        errorMessage.innerHTML = res.message;
    }
}

function printAllDeck(decks) {
    decks.forEach((item) => {
        console.log(item)
        deckList.insertAdjacentHTML("beforeend", createTemplate(Template.deck, {"id" : item.id, "value" : item.name}));
        item.cards.forEach((card) => {
            document
                .getElementById(`deck-cards-${item.id}`)
                .insertAdjacentHTML("beforeend", createTemplate(Template.card, {"value" : card.text}));
        });
        initCardButtons(item.id);
    });
    errorMessage.innerHTML = "";
}

function initCardButtons(deckId) {
    document
        .getElementById(`add-card-btn-${deckId}`)
        .addEventListener("click", () => {
            addCardButtonListener(deckId);
        });

    document
        .getElementById(`save-card-${deckId}`)
        .addEventListener("click", () => {
            saveCard(deckId).then(appendCard);
            closeCardForm(deckId);
        });

    document
        .getElementById(`cancel-card-${deckId}`)
        .addEventListener("click", () => {
            closeCardForm(deckId);
        });
}

function getBoard() {
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

function printBoardName(boardName) {
    boardUsername.innerHTML = boardName
    errorMessage.innerHTML = "";
}

function initControls() {
    addDeckButton.addEventListener("click", openDeckForm);
    closeDeckButton.addEventListener("click", (e) => {
        e.preventDefault();
        closeDeckForm();
    });

    saveButton.addEventListener("click", (e) => {
        e.preventDefault();
        saveDeck().then(appendDeck);
        closeDeckForm();
    });

    document.addEventListener("DOMContentLoaded", () => {
        getBoard().then(printBoard);
    });
}

initControls();
