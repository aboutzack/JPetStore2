import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import ElementUI from 'element-ui'
import VueCookies from 'vue-cookies'
import './plugins/element-variables.scss'
import store from './store'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(VueAxios, axios)
Vue.use(VueCookies)

axios.defaults.baseURL='/api/v1/'

window.addEventListener('beforeunload',()=>{
  localStorage.setItem('signed',store.state.signed)
})
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})
new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
