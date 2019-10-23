import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
import './plugins/element-variables.scss'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(VueAxios, axios)

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})
new Vue({
  render: h => h(App),
  router
}).$mount('#app')
