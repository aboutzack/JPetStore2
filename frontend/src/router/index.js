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
        },
        {
            path: '/cart',
            name: '购物车',
            hidden: true,
            meta: {
                title: 'JPetStore - Cart',
            },
            component: () => import('../pages/cart.vue')
        },
        {
            path: '/product/searches',
            name: '搜索',
            hidden: true,
            meta: {
                title: 'JPetStore - Search',
            },
            component: () => import('../pages/search.vue')
        },
        {
            path: '/neworder',
            name: '新订单',
            hidden: true,
            meta: {
                title: 'JPetStore - NewOrder',
            },
            component: () => import('../pages/new-order.vue')
        },
        {
            path: '/user/detailinfo',
            name: '详细信息',
            hidden: true,
            meta: {
                title: 'JPetStore - DetailInfo',
            },
            component: () => import('../pages/detailinfo.vue')
        },
    ]
})
