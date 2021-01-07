// vue.config.js
module.exports = {
    outputDir: '../shop-backend/src/main/resources/public',
    runtimeCompiler: true,
    chainWebpack: config => {
        config.module.rules.delete('eslint');
    },
    devServer: {
        port: 80,
        proxy: {
            '/api': {
                target: 'http://localhost:8088',
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/api': '/api'
                }
            },
            '/cdn': {
                target: 'http://localhost:8088',
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/cdn': '/'
                }
            }
        }
    }
}
