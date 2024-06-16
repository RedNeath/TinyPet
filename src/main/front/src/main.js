import { createApp } from 'vue'
import { createMemoryHistory, createRouter } from 'vue-router';

import App from './App.vue'
import RecentPetitionsView from './components/views/RecentPetitionsView.vue'
import TaggedPetitionsView from './components/views/TaggedPetitionsView.vue'
import './index.css'

const routes = [
    { path: "/", name: 'home', component: RecentPetitionsView },
    { path: "/search/:tags", name: 'search', component: TaggedPetitionsView },
    { path: "/petition/:petitionId", name: 'petition', component: RecentPetitionsView },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes
});

createApp(App)
    .use(router)
    .mount('#app')
