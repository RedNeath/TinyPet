import { createApp } from 'vue'
import { createMemoryHistory, createRouter } from 'vue-router';

import App from './App.vue'
import RecentPetitionsView from './components/views/RecentPetitionsView.vue'
import './index.css'

const routes = [
    { path: "/", name: 'home', component: RecentPetitionsView },
    { path: "/petition/:petitionId", name: 'petition', component: RecentPetitionsView },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes
});

createApp(App)
    .use(router)
    .mount('#app')
