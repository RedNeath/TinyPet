<template>
  <header class="bg-slate-50 dark:bg-slate-900 border-b dark:border-slate-700">
    <nav class="mx-auto grid grid-cols-2 gap-y-6 md:grid-cols-7 max-w-7xl items-center justify-between p-4 md:p-6 lg:px-8" aria-label="Global">
      <div class="flex lg:flex-1">
        <RouterLink :to="{ name: 'home' }" class="-m-1.5 p-1.5 flex">
          <span class="sr-only">TinyPet</span>
          <img class="h-8 w-auto" src="../assets/tinypet.png" alt="" />
          <span class="ml-3 text-2xl font-bold text-slate-900 dark:text-slate-200">TinyPet</span>
        </RouterLink>
      </div>
      <div class="flex md:hidden ml-auto">
        <GoogleSignInButton v-if="connectionToken == null" @success="handleLoginSuccess" @error="handleLoginError" class="overflow-hidden text-ellipsis whitespace-nowrap max-w-40 rounded-lg border border-zinc-300"/>
        <button v-else type="button" @click="goToProfile()" class="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-slate-900 dark:text-slate-200">
          <span class="sr-only">Go to profile page</span>
          <img v-if="avatarUrl != null" :src="avatarUrl" class="size-9 rounded-full">
          <svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-9">
            <path fill-rule="evenodd" d="M18.685 19.097A9.723 9.723 0 0 0 21.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 0 0 3.065 7.097A9.716 9.716 0 0 0 12 21.75a9.716 9.716 0 0 0 6.685-2.653Zm-12.54-1.285A7.486 7.486 0 0 1 12 15a7.486 7.486 0 0 1 5.855 2.812A8.224 8.224 0 0 1 12 20.25a8.224 8.224 0 0 1-5.855-2.438ZM15.75 9a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0Z" clip-rule="evenodd" />
          </svg>
        </button>
      </div>
      <div id="searchbar" class="col-span-4 md:col-end-8 md:flex md:justify-end md:ml-auto">
        <label for="research" class="sr-only block text-sm font-medium leading-6 text-gray-900">Browse for a petition</label>
        <div class="relative rounded-md shadow-sm md:mr-2">
          <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-2 md:pl-3">
            <span class="text-slate-500 sm:text-sm">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5 md:size-4">
                <path fill-rule="evenodd" d="M9 3.5a5.5 5.5 0 1 0 0 11 5.5 5.5 0 0 0 0-11ZM2 9a7 7 0 1 1 12.452 4.391l3.328 3.329a.75.75 0 1 1-1.06 1.06l-3.329-3.328A7 7 0 0 1 2 9Z" clip-rule="evenodd" />
              </svg>
            </span>
          </div>
          <input type="text" name="research" v-model="searchedTags" id="research" @keyup.enter="search" class="block w-full rounded-md border-0 py-1.5 pl-8 pr-2 md:pr-16 text-slate-900 ring-1 ring-inset ring-gray-300 placeholder:text-slate-400 focus:ring-2 focus:ring-inset focus:ring-teal-400 sm:text-sm sm:leading-6 bg-white dark:bg-slate-100" placeholder="Browse for petitions...">
          <div class="hidden md:block absolute inset-y-0 right-0 flex items-center">
            <label for="search-action" class="sr-only">Search</label>
            <div class="h-full p-[5px]">
              <button id="search-action" @click="search" name="search-action" class="h-full rounded-md border-0 bg-teal-600 text-slate-200 focus:ring-2 focus:ring-teal-400 hover:bg-teal-500 text-xs px-2">Search</button>
            </div>
          </div>
        </div>
        <div class="hidden md:flex lg:flex-1 lg:justify-end my-auto items-center">
          <GoogleSignInButton v-if="connectionToken == null" @success="handleLoginSuccess" @error="handleLoginError" class="max-h-9" />
          <button v-else type="button" @click="goToProfile()" class="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-slate-900 dark:text-slate-200">
            <span class="sr-only">Go to profile page</span>
            <img v-if="avatarUrl != null" :src="avatarUrl" class="size-9 rounded-full">
            <svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-9">
              <path fill-rule="evenodd" d="M18.685 19.097A9.723 9.723 0 0 0 21.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 0 0 3.065 7.097A9.716 9.716 0 0 0 12 21.75a9.716 9.716 0 0 0 6.685-2.653Zm-12.54-1.285A7.486 7.486 0 0 1 12 15a7.486 7.486 0 0 1 5.855 2.812A8.224 8.224 0 0 1 12 20.25a8.224 8.224 0 0 1-5.855-2.438ZM15.75 9a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0Z" clip-rule="evenodd" />
            </svg>
          </button>
        </div>
      </div>
    </nav>
  </header>
</template>

<script>
import { GoogleSignInButton } from 'vue3-google-signin';
import VueJwtDecode from 'vue-jwt-decode';

export default {
  name: 'NavigationBar',
  components: {
    GoogleSignInButton
  },

  data() {
    return {
      searchedTags: "",
      connectionToken: null,
      email: null,
      avatarUrl: null,
    };
  },

  methods: {
    search() {
      if (this.searchedTags !== "") {
        this.$router.push({ name: "search", params: { tags: this.searchedTags }});
      }
    },

    goToProfile() {
      this.$router.push({ name: "profile" });
    },

    handleLoginSuccess(response) {
      let decodedToken = VueJwtDecode.decode(response.credential);

      this.connectionToken = response.credential;
      this.email = decodedToken.email;
      this.avatarUrl = decodedToken.picture;

      localStorage.setItem("TinyPetGoogleToken", this.connectionToken);
      localStorage.setItem("TinyPetEmail", this.email);
      localStorage.setItem("TinyPetAvatarUrl", this.avatarUrl);
    },

    handleLoginError() {
      alert("Connection with google failed!");
    }
  },

  mounted() {
    // These will be null if they do not exist.
    // In the case these are null, we will display the google connection button. Otherwise we will
    // display the avatar, or the base placeholder if the account doesn't have an avatar.
    this.connectionToken = localStorage.getItem("TinyPetGoogleToken");
    this.email = localStorage.getItem("TinyPetEmail");
    this.avatarUrl = localStorage.getItem("TinyPetAvatarUrl");
  }
}
</script>