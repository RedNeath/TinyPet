<template>
    <div id="petition-container" class="md:pt-1">
        <div v-for="petition in petitions" :key="petition.key" class="border dark:border-slate-600 dark:bg-slate-700 rounded-lg p-3 mt-4 md:mt-3 md:pb-5 md:px-1 shadow dark:shadow-md md:border-0 md:border-b md:dark:bg-slate-800 md:shadow-none md:rounded-none">
            <div class="flex md:hidden overflow-x-auto">
                <TagBadge :link="{ name: 'search', params: { tags: tag }}" v-for="tag in petition.properties.tags" :key="tag" :label="tag" :mobile="true"></TagBadge>
            </div>
            <div class="md:flex">
                <RouterLink :to="{ name: 'petition', params: { petitionId: petition.key.id }}" class="text-xl font-medium dark:text-slate-100 md:text-2xl md:hover:underline md:hover:text-teal-500">{{ petition.properties.name }}</RouterLink>
                <div class="hidden md:flex ml-3">
                    <TagBadge :link="{ name: 'search', params: { tags: tag }}" v-for="tag in petition.properties.tags" :key="tag" :label="tag" :mobile="false"></TagBadge>
                </div>
            </div>

            <p class="my-1 text-sm dark:text-slate-200 md:text-base">{{ petition.properties.description }}</p>
            <span class="md:hidden text-sm dark:text-slate-200 md:dark:text-slate-300">{{ "Ends on: " + new Date(petition.properties.end_date).toLocaleDateString() }}</span>
            <div class="hidden md:flex justify-between dark:text-slate-300">
                <span>{{ new Date(petition.properties.beg_date).toLocaleDateString() + " - " + new Date(petition.properties.end_date).toLocaleDateString() }}</span>
                <span>{{ "Target: " + petition.properties.signature_target }}</span>
            </div>

            <RouterLink :to="{ name: 'petition', params: { petitionId: petition.key.id }}" class="md:hidden w-full h-full mt-2 rounded-md border-0 bg-teal-600 text-slate-200 focus:ring-2 focus:ring-teal-400 hover:bg-teal-500 text-sm font-medium py-1 px-3 flex items-center justify-center">
                See more
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-4 ml-1">
                    <path fill-rule="evenodd" d="M3 10a.75.75 0 0 1 .75-.75h10.638L10.23 5.29a.75.75 0 1 1 1.04-1.08l5.5 5.25a.75.75 0 0 1 0 1.08l-5.5 5.25a.75.75 0 1 1-1.04-1.08l4.158-3.96H3.75A.75.75 0 0 1 3 10Z" clip-rule="evenodd" />
                </svg>
            </RouterLink>
        </div>
    </div>
</template>

<script>
import { RouterLink } from 'vue-router';
import TagBadge from './TagBadge.vue';

export default {
    name: 'PetitionList',
    components: {
        RouterLink,
        TagBadge,
    },

    props: {
        petitions: Array
    },
}
</script>
