const path = require('path');

module.exports = {
    entry: './js/index.js',
    output: {
        path: path.resolve(__dirname, './js/dist'),
        filename: 'bundle.js'
    },
    devServer: {
        host: "0.0.0.0",
        disableHostCheck: true
    }
};
