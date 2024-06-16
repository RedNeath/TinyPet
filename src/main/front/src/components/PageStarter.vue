<template>
    <!-- Breadcrumbs -->
    <div id="full-breadcrumbs" class="hidden md:flex mt-16">
        <RouterLink v-for="breadcrumb in breadcrumbs" :key="breadcrumb.label" :to="breadcrumb.path" class="flex text-sm text-slate-900 dark:text-slate-200 hover:text-slate-600 dark:hover:text-slate-400 ml-2">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5 mr-2">
                <path v-if="breadcrumb.path === '/'" fill-rule="evenodd" d="M9.293 2.293a1 1 0 0 1 1.414 0l7 7A1 1 0 0 1 17 11h-1v6a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1v-3a1 1 0 0 0-1-1H9a1 1 0 0 0-1 1v3a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1v-6H3a1 1 0 0 1-.707-1.707l7-7Z" clip-rule="evenodd" />
                <path v-else fill-rule="evenodd" d="M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
            </svg>
            {{ breadcrumb.label }}
        </RouterLink>
    </div>
    <div id="mobile-breadcrumbs" v-if="breadcrumbs.length > 1" class="md:hidden mt-5">
        <RouterLink :to="breadcrumbs[breadcrumbs.length - 2].path" class="flex text-slate-900 dark:text-slate-200">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-6 mr-2">
                <path fill-rule="evenodd" d="M11.78 5.22a.75.75 0 0 1 0 1.06L8.06 10l3.72 3.72a.75.75 0 1 1-1.06 1.06l-4.25-4.25a.75.75 0 0 1 0-1.06l4.25-4.25a.75.75 0 0 1 1.06 0Z" clip-rule="evenodd" />
            </svg>
            {{ "Go back to " + breadcrumbs[breadcrumbs.length - 2].label }}
        </RouterLink>
    </div>
    <h1 class="mt-7 text-2xl font-bold text-slate-900 dark:text-slate-200 md:text-3xl">{{ title }}</h1>
</template>

<script>
import { RouterLink } from 'vue-router';

export default {
    name: 'PageStarter',
    components: {
        RouterLink,
    },

    props: {
        title: String,
        breadcrumbs: Array
    },
}
</script>
