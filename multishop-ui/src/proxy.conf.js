const PROXY_CONFIG = [
  {
    context: [
      "/api",
      "/h2",
      "/resource",
      "/assets"
    ],
    target: "https://piana.ir:8443",
    // target: "https://localhost:8443",
    secure: false,
    changeOrigin: false,
    // cookieDomainRewrite: "localhost",
    // cookieDomainRewrite: "vavishka.piana.ir",
    // cookieDomainRewrite: req.headers['Host'],
    withCredentials: false,
    /*bypass: function(req, res, proxyOptions) {
      req.headers['Host'] = 'shop.piana.ir'
    },*/
    onProxyRes: (proxyRes, req, res) => {
      // console.log(proxyRes);
      // console.log(req.headers['Set-Cookie']);
      // console.log(res);
      // console.log(res.headers['Set-Cookie']);
      // proxyRes.headers['x-added'] = 'foobar'; // add new header to response
    }
  }
]

module.exports = PROXY_CONFIG;
