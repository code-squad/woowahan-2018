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

        if(dom === null) {
            return;
        }

        dom.addEventListener(event, callback);
    }
}

const boardUtils = {
    createTemplate(html, data) {
        return html.replace(/{{(\w*)}}/g, (m, key) => {
            return data.hasOwnProperty(key) ? data[key] : "";
        })
    }
}

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
        DECKS(boardId) {
            return `/api/boards/${boardId}/decks`;
        },
        CARDS(boardId, deckId) {
            return `/api/boards/${boardId}/decks/${deckId}/cards`;
        }
    }

}

export { _, boardUtils, API };
