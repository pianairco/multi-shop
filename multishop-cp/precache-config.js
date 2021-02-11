var SWPrecacheWebpackPlugin = require('sw-precache-webpack-plugin');

module.exports = {
  navigateFallback: '/index.html',
  navigateFallbackWhitelist: [],
  stripePrefix: '../shop-backend/src/main/resources/control-panel',
  root: '../shop-backend/src/main/resources/control-panel/',
  plugins:[
    new SWPrecacheWebpackPlugin({
      cacheId: 'control-panel',
      filename: 'service-worker.js',
      staticFileGlobs: [
        '../shop-backend/src/main/resources/control-panel/index.html',
        '../shop-backend/src/main/resources/control-panel/**.js',
        '../shop-backend/src/main/resources/control-panel/**.css'
      ],

    })
  ],
  stripePrefix: '../shop-backend/src/main/resources/control-panel/assets',
  mergeStaticsConfig: true
};
