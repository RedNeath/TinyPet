import { createApp } from 'vue'
import { createMemoryHistory, createRouter } from 'vue-router';

import App from './App.vue'
import RecentPetitionsView from './components/views/RecentPetitionsView.vue'
import './index.css'

const routes = [
    { path: "/", component: RecentPetitionsView }
];

const router = createRouter({
  history: createMemoryHistory(),
  routes
});

createApp(App)
    .use(router)
    .mount('#app')
