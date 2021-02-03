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
                // target: 'https://piana.ir:8443',
                target: 'https://localhost:8443',
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/api': '/api'
                }
            },
            '/cdn': {
                // target: 'https://piana.ir',
                // target: 'https://piana.ir:8443',
                target: 'https://localhost:8443',
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/cdn': '/cdn'
                }
            },
            '/resources': {
                // target: 'https://piana.ir',
                // target: 'https://piana.ir:8443',
                target: 'https://localhost:8443',
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/resources': '/resources'
                }
            },
            '/h2': {
                // target: 'https://piana.ir',
                // target: 'https://piana.ir:8443',
                target: 'https://localhost:8443',
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/h2': '/h2'
                }
            }
        }
    }
}
