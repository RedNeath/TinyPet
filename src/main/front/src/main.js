import { createApp } from 'vue'
import { createMemoryHistory, createRouter } from 'vue-router';
import GoogleSignInPlugin from 'vue3-google-signin';

import App from './App.vue'
import RecentPetitionsView from './components/views/RecentPetitionsView.vue'
import TaggedPetitionsView from './components/views/TaggedPetitionsView.vue'
import PetitionDetailsView from './components/views/PetitionDetailsView.vue'
import ProfileView from './components/views/ProfileView.vue'
import CreatePetitionView from './components/views/CreatePetitionView.vue'
import './index.css'

const routes = [
    { path: "/", name: 'home', component: RecentPetitionsView },
    { path: "/search/:tags", name: 'search', component: TaggedPetitionsView },
    { path: "/petition/:petitionId", name: 'petition', component: PetitionDetailsView },
    { path: "/profile", name: 'profile', component: ProfileView },
    { path: "/create-petition", name: 'create-petition', component: CreatePetitionView },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes
});

createApp(App)
    .use(router)
    .use(GoogleSignInPlugin, { clientId: '440998180387-r3rk5d6lbn3h9u1dksplncu8fdrdqctl.apps.googleusercontent.com', })
    .mount('#app')
