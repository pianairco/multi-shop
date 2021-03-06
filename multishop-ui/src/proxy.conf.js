const PROXY_CONFIG = [
  {
    context: [
      "/api",
      "/h2",
      "/resource",
      "/assets"
    ],
    target: "https://shop.piana.ir:8443",
    // target: "https://shop.piana.ir:8443",
    secure: false,
    changeOrigin: true,
    // cookieDomainRewrite: "localhost",
    cookieDomainRewrite: "shop.piana.ir",
    withCredentials: false,
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
