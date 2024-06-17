<template>
    <RouterLink v-if="link !== null" :to="link" :class="getClasses()">
        {{ label }}
    </RouterLink>

    <span v-else :class="getClasses()">
        {{ label }}
    </span>
</template>

<script>
import { RouterLink } from 'vue-router';

const classes = { "inline-flex whitespace-nowrap flex-nowrap items-center rounded-full bg-teal-100 dark:bg-teal-900 px-1.5 py-0.5 text-xs font-medium text-slate-600 dark:text-slate-300 ring-1 ring-inset ring-teal-500/10 dark:ring-teal-100/10 mr-1 my-auto": true };
const mobileClasses = { "inline-flex whitespace-nowrap flex-nowrap items-center rounded-full bg-teal-100 dark:bg-teal-900 px-2 py-1 text-xs font-medium text-slate-600 dark:text-slate-300 ring-1 ring-inset ring-teal-500/10 dark:ring-teal-100/10 mr-1 mb-1": true };

export default {
    name: "TagBadge",
    component: {
        RouterLink
    },

    props: {
        label: String,
        link: {
            type: Object,
            required: false,
            default: null
        },
        mobile: Boolean
    },

    methods: {
        getClasses() {
            return this.mobile? mobileClasses: classes;
        }
    }
}
</script>