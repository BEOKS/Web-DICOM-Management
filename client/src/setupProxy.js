const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app){
  app.use(
      createProxyMiddleware('/MetaData', {
          target: 'http://localhost:8080',
          changeOrigin: true
      })
  )
  app.use(
    createProxyMiddleware('/api', {
        target: 'http://localhost:8080',
        changeOrigin: true
    })
)
};