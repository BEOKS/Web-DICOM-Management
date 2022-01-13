const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app){
  app.use(
      createProxyMiddleware('/MetaData', {
          target: 'http://155.230.29.41:8080',
          changeOrigin: true
      })
  )
  app.use(
    createProxyMiddleware('/api', {
        target: 'http://155.230.29.41:8080',
        changeOrigin: true
    })
)
};