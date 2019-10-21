module.exports = {
    devServer: {
        // open: true,
        host: '127.0.0.1',
        port: 3000,
        https: false,
        proxy: {
            "/api": {
                target: "http://127.0.0.1:8090", //设置调用的接口域名和端口
                changeOrigin: true, //是否跨域
                ws: true,//websocket
            }
        },
    },
    configureWebpack: {
        devtool: 'source-map'
    }
}