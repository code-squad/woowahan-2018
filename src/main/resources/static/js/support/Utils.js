import MSG from "../../message.json";

const _ = {
    request(url, httpMethod, parameters) {
        return new Promise((resolve) => {
            let xhr = new XMLHttpRequest();
            xhr.open(httpMethod, url, true);
            xhr.addEventListener("load", () => {
                const res = JSON.parse(xhr.response);

                if (!Array.isArray(res)) {
                    res.message = this._byString(MSG, res.message);
                } else {
                    res.forEach((data) => data.message = this._byString(MSG, data.message));
                }

                resolve(res);
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
    } ,

    _byString(object, string) {
        string = string.replace(/\[(\w+)\]/g, '.$1'); // convert indexes to properties
        string = string.replace(/^\./, '');           // strip a leading dot
        var tokens = string.split('.');
        for (var i = 0, n = tokens.length; i < n; ++i) {
            var k = tokens[i];
            if (k in object) {
                object = object[k];
            } else {
                return;
            }
        }
        return object;
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
	    CARD_MOVE(cardId) {
		    return API.BOARDS.CARDS() + `/${cardId}/move`;
	    },
        CARD_DESCRIPTION(cardId) {
            return API.BOARDS.CARD(cardId) + `/description`;
        },
        CARD_DATE(cardId) {
            return API.BOARDS.CARD(cardId) + `/date`;
        },
        CARD_ASSIGNEE(cardId) {
            return API.BOARDS.CARD(cardId) + `/assignees`;
        },
        ADDMEMBER(boardId) {
            return `/api/boards/${boardId}/members`;
        },
        COMMENTS() {
            return `/api/comments`;
        },
        COMMENT(commentId) {
            return `/api/comments/${commentId}`;
        }
    }
};

export {_, boardUtils, API};
