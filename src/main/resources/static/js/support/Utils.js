class Utils {
    static ajax(url, httpMethod, parameters) {
        return new Promise((resolve) => {
            let xhr = new XMLHttpRequest();
            xhr.open(httpMethod, url, true);
            xhr.addEventListener("load", () => {
            	resolve(JSON.parse(xhr.response));
            });
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.send(JSON.stringify(parameters));
        });
    }

    static $(selector) {
        return document.querySelector(selector);
    }

    static eventHandler(selector, event, callback) {
        const dom = this.$(selector);

        if(dom === null) {
            return;
        }

        dom.addEventListener(event, callback);
    }
}

export default Utils;
