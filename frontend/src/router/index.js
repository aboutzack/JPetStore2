import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: '首页',
            title: 'JPetStore',
            hidden: true,
            meta: {
                title: 'JPetStore'
            },
            component: () => import('../pages/index.vue')
        }, {
            path: '/signin',
            name: '登录',
            hidden: true,
            meta: {
                title: 'JPetStore - 登录',
            },
            component: () => import('../pages/signin.vue')
        },{
            path: '/signup',
            name: '注册',
            hidden: true,
            meta: {
                title: 'JPetStore - 注册',
            },
            component: () => import('../pages/signup.vue')
        },
        {
            path: '/category',
            name: '种类',
            hidden: true,
            meta: {
                title: 'JPetStore - Category',
            },
            component: () => import('../pages/category.vue')
        },
        {
            path: '/product',
            name: '商品',
            hidden: true,
            meta: {
                title: 'JPetStore - Product',
            },
            component: () => import('../pages/product.vue')
        },
        {
            path: '/item',
            name: '具体商品',
            hidden: true,
            meta: {
                title: 'JPetStore - Item',
            },
            component: () => import('../pages/item.vue')
        }
    ]
})
