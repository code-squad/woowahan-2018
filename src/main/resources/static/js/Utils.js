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
}

export default Utils;
