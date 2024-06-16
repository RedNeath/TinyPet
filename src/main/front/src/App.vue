<template>
  <NavigationBar />
  <main class="px-4 md:px-16 lg:px-48">
    <RouterView/>
  </main>
</template>

<script>
import NavigationBar from './components/NavigationBar.vue';

export default {
  name: 'App',
  components: {
    NavigationBar
  }
};
</script>
