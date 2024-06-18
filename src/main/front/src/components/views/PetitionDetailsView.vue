<template>
    <PageStarter
        :tags="petition.properties.tags"
        :title="petition.properties.name" 
        :breadcrumbs="[{ label: 'Home', path: '/' }, { label: petition.properties.name, path: '/petition/' + petition.key.id }]" 
        :actions="hasSigned? []: [{ label: 'Sign', trigger: signPetition }]"
    />
    <TagBadge v-for="tag in petition.properties.tags" :key="tag" :label="tag" :mobile="true" class="md:hidden mt-2"></TagBadge>
    <button v-if="!hasSigned" @click="signPetition()" class="md:hidden w-full mt-6 rounded-md border-0 bg-teal-600 text-slate-200 focus:ring-2 focus:ring-teal-400 hover:bg-teal-500 text-sm font-medium py-1 px-3">Sign this petition</button>

    <div id="petition-content" class="pt-4 md:p-1">
        <p class="text-slate-900 dark:text-slate-200 mb-4 md:font-light">{{ "By " + petition.properties.author }}</p>

        <span class="text-xs font-thin text-slate-900 dark:text-slate-300">Description</span>
        <p class="text-slate-900 dark:text-slate-200 mb-4">{{ petition.properties.description }}</p>

        <span class="text-xs font-thin text-slate-900 dark:text-slate-300">Starting date - Ending date</span>
        <p class="text-slate-900 dark:text-slate-200 mb-4">{{ new Date(petition.properties.beg_date).toLocaleDateString() + " - " + new Date(petition.properties.end_date).toLocaleDateString() }}</p>
    </div>
    <div id="petition-signatures" class="pt-4">
        <div class="flex justify-between">
            <span class="text-xs font-thin text-slate-900 dark:text-slate-300">Signatures</span>
            <span class="text-xs font-thin text-slate-900 dark:text-slate-300">{{ signatories.items.length + " of " + petition.properties.signature_target }}</span>
        </div>
        <div class="w-full bg-slate-200 rounded-full h-1.5 mb-4 dark:bg-slate-700">
            <div class="bg-teal-600 h-1.5 rounded-full dark:bg-teal-500" :style="{ width: getPercentage() + '%' }"></div>
        </div>

        <div id="signatures" class="mb-4">
            <div v-for="signature in signatories.items" :key="signature.key.id" class="flex justify-between items-center mb-2 md:p-3 md:text-sm md:rounded-lg md:bg-teal-50 md:dark:bg-slate-900">
                <div class="text-slate-900 dark:text-slate-200 flex items-center">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-7 mr-2">
                        <path fill-rule="evenodd" d="M18.685 19.097A9.723 9.723 0 0 0 21.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 0 0 3.065 7.097A9.716 9.716 0 0 0 12 21.75a9.716 9.716 0 0 0 6.685-2.653Zm-12.54-1.285A7.486 7.486 0 0 1 12 15a7.486 7.486 0 0 1 5.855 2.812A8.224 8.224 0 0 1 12 20.25a8.224 8.224 0 0 1-5.855-2.438ZM15.75 9a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0Z" clip-rule="evenodd" />
                    </svg>
                    <span class="hidden md:block mr-2">Signed by</span><span>{{ signature.properties.user }}</span>
                </div>
                <div class="flex items-center">
                    <span class="hidden md:block mr-2 text-slate-900 dark:text-slate-200">on</span>
                    <span class="text-slate-900 dark:text-slate-200">{{ new Date(signature.properties.date).toLocaleDateString() }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import PageStarter from '../PageStarter.vue';
import TagBadge from '../TagBadge.vue';

export default {
    name: 'PetitionDetailsView',
    components: {
        PageStarter,
        TagBadge,
    },

    data() {
        return {
            petition: { 
                properties: { 
                    name: "Loading...",
                    tags: []
                },
                key: { id: 0 } 
            },

            signatories: { items: [] },
            hasSigned: false,
        }
    },

    methods: {
        signPetition() {
            fetch("https://cloudmodule-414213.oa.r.appspot.com/_ah/api/tinyPetApi/ca-marche-sur-mon-pc/signPetition/" + this.petition.key.id,
                { method: "POST", headers: { Email: "test@example.com" } }
            )
                .then(response => response.json())
                .then(data => {
                    this.signatories.items.unshift(data);
                    this.hasSigned = true;
                });
        },

        getPercentage() {
            if (this.petition.signature_target == 0) return 100;
            
            return 100 * this.signatories.items.length / this.petition.properties.signature_target;
        }
    },

    mounted() {
        fetch("https://cloudmodule-414213.oa.r.appspot.com/_ah/api/tinyPetApi/ca-marche-sur-mon-pc/petition/" + this.$route.params.petitionId)
            .then(response => response.json())
            .then(data => this.petition = data);

        fetch("https://cloudmodule-414213.oa.r.appspot.com/_ah/api/tinyPetApi/ca-marche-sur-mon-pc/petitionSignatories/" + this.$route.params.petitionId)
            .then(response => response.json())
            .then(data => {
                this.signatories = data;
                this.hasSigned = this.signatories.items.find(sgt => sgt.properties.user == "test@example.com") != undefined;
            });
    }
}
</script>
