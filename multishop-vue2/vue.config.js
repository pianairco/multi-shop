// vue.config.js
module.exports = {
    outputDir: '../shop-backend/src/main/resources/public',
    runtimeCompiler: true,
    chainWebpack: config => {
        config.module.rules.delete('eslint');
    },
    devServer: {
        host: 'localhost',
        // host: 'piana.ir',
        https: true,
        port: 443,
        proxy: {
            '/api': {
                // target: 'https://piana.ir',
                // target: 'https://piana.ir:8043',
                target: 'https://localhost:8043',
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/api': '/api'
                }
            },
            '/cdn': {
                // target: 'https://piana.ir',
                // target: 'https://piana.ir:8043',
                target: 'https://localhost:8043',
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/cdn': '/'
                }
            },
            '/resources': {
                // target: 'https://piana.ir',
                // target: 'https://piana.ir:8043',
                target: 'https://localhost:8043',
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/resources': '/resources'
                }
            }
        }
    }
}
