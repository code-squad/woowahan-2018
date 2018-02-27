const _ = {
    ajax(url, httpMethod, parameters) {
        return new Promise((resolve) => {
            let xhr = new XMLHttpRequest();
            xhr.open(httpMethod, url, true);
            xhr.addEventListener("load", () => {
                resolve(JSON.parse(xhr.response));
            });
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.send(JSON.stringify(parameters));
        });
    },

    $(selector) {
        return document.querySelector(selector);
    },

    eventHandler(selector, event, callback) {
        const dom = this.$(selector);

        if (dom === null) {
            return;
        }

        dom.addEventListener(event, callback);
    }
};

const boardUtils = {
    createTemplate(html, data) {
        return html.replace(/{{(\w*)}}/g, (m, key) => {
            return data.hasOwnProperty(key) ? data[key] : "";
        })
    }
};

const API = {
    USERS: {
        SIGNUP: "/api/users",
        LOGIN: "/api/users/login",
        LOGOUT: "/api/users/logout"
    },
    BOARDS: {
        MYBOARD: "/api/boards",
        BOARD(boardId) {
            return `/api/boards/${boardId}`;
        },
        DECKS() {
            return `/api/decks`;
        },
        CARDS() {
            return `/api/cards`;
        },
        CARD(cardId) {
            return API.BOARDS.CARDS() + `/${cardId}`;
        },
        CARD_DESCRIPTION(cardId) {
            return API.BOARDS.CARD(cardId) + `/description`;
        },
        ADDMEMBER(boardId) {
            return `/api/boards/${boardId}/members`;
        }
    }
};

export {_, boardUtils, API};
