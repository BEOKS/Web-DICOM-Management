const { createProxyMiddleware } = require('http-proxy-middleware');

const hostLocation=process.env.REACT_APP_SERVER_HOST
module.exports = function (app) {
    app.use(
        createProxyMiddleware('/MetaData', {
            target: `http://${hostLocation}:8080`,
            changeOrigin: true
        })
    )
    app.use(
        createProxyMiddleware('/api', {
            target: `http://${hostLocation}:8080`,
            changeOrigin: true
        })
    )
    app.use(
        createProxyMiddleware('/oauth2', {
            target: `http://${hostLocation}:8080`,
            changeOrigin: true
        })
    )
    app.use(
        createProxyMiddleware('/logout', {
            target: `http://${hostLocation}:8080`,
            changeOrigin: true
        })
    )
};