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
    <div id="page-tags" v-if="tags.length > 0" class="hidden md:flex mt-7 mb-1">
        <TagBadge v-for="tag in tags" :key="tag" :label="tag" :mobile="false"></TagBadge>
    </div>
    <div class="flex justify-between">
        <h1 :class="{ 'mt-7 text-2xl font-bold text-slate-900 dark:text-slate-200 md:text-3xl': true, 'md:mt-0': tags.length > 0 }">{{ title }}</h1>
        <div class="hidden md:flex my-auto">
            <button v-for="action in actions" :key="action" @click="action.trigger" class="ml-2 rounded-md border-0 bg-teal-600 text-slate-200 focus:ring-2 focus:ring-teal-400 hover:bg-teal-500 font-medium text-base py-1.5 px-4">
                {{ action.label }}
            </button>
        </div>
    </div>
</template>

<script>
import { RouterLink } from 'vue-router';
import TagBadge from './TagBadge.vue';

export default {
    name: 'PageStarter',
    components: {
        RouterLink,
        TagBadge,
    },

    props: {
        tags: {
            type: Array,
            required: false,
            default: new Array()
        },
        title: String,
        breadcrumbs: Array,
        actions: {
            type: Array,
            required: false
        },
    },
}
</script>
