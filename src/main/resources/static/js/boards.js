const addBoardBtn = document.querySelector('.add-board-btn');
const closeModalBtn = document.querySelector('.close-modal');
const saveBtn = document.querySelector('.save');
const boardListDom = document.querySelector('.board-list');
const modalDiv = document.querySelector('#modal');
const nameDom = document.querySelector('.board-name');

function openModal() {
  modalDiv.classList.add('open');
}

function closeModal() {
  modalDiv.classList.remove('open');
}

function saveBoard() {
  let promise;
  promise = new Promise((resolve, timeLimit) => {
    let xhr = new XMLHttpRequest();
    xhr.open("post", "/api/boards", true);
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

function appendBoard(res) {
  let status = res.status;
  console.log(res);
  if (status === "OK") {
    boardListDom.innerHTML += createTemplate(Template.board, {'name': nameDom.value});
    closeModal();
    nameDom.value = "";
  } else {
    const warning = document.querySelector('.warning');
    warning.innerHTML = res.message;
    warning.style.display = 'block';
  }
}

function createTemplate(html, data) {
  return html.replace(/{{(\w*)}}/g, function (m, key) {
    return data.hasOwnProperty(key) ? data[key] : "";
  });
}

function getExistBoards() {
  let promise;
  promise = new Promise((resolve, timeLimit) => {
    let xhr = new XMLHttpRequest();
    xhr.open("get", "/api/boards", true);
    xhr.addEventListener("load", (e) => {
      console.log(xhr);
      resolve(JSON.parse(xhr.response));
    });
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
  });

  return promise;
}

function printAllBoard(res) {
  const boards = res.boards;

  boards.forEach((item) => {
    boardListDom.innerHTML += createTemplate(Template.board, {'id': item.id, 'name': item.name});
  });
}

addBoardBtn.addEventListener('click', openModal);
closeModalBtn.addEventListener('click', closeModal);
saveBtn.addEventListener('click', (e) => {
  saveBoard()
    .then(appendBoard);
});
document.addEventListener('DOMContentLoaded', function () {
  getExistBoards().then(printAllBoard);
});