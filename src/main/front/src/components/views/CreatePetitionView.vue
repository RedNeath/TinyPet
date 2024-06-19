<template>
    <PageStarter
        title="Create a petition"
        :breadcrumbs="[{ label: 'Home', path: '/' }, { label: 'My profile', path: '/profile' }, { label: 'Create a petition', path: '/create-petition'}]"
    />

    <div id="incorrect-data-alert" v-if="incorrectSubmit" class="flex items-center p-2 my-4 text-red-800 rounded-lg bg-red-50 dark:bg-red-300/75 dark:text-red-900" role="alert">
        <svg class="flex-shrink-0 w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
            <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
        </svg>
        <span class="sr-only">Info</span>
        <div class="ms-3 text-sm font-medium">Please fill <span class="font-semibold">all</span> mandatory fields in the form below (marked by a red *). Note that the signature target can not be 0 or below!</div>
        <button type="button" @click="incorrectSubmit = !incorrectSubmit" class="ms-auto -mx-1.5 -my-1.5 bg-red-50 text-red-500 rounded-lg focus:ring-2 focus:ring-red-400 p-1.5 hover:bg-red-200 inline-flex items-center justify-center h-8 w-8 dark:bg-red-300/0 dark:text-red-900 dark:hover:bg-red-300/50">
            <span class="sr-only">Close</span>
                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                </svg>
        </button>
    </div>

    <form @submit.prevent="uploadPetition()">
        <div class="border-b border-slate-900/10 dark:border-slate-600 pb-6 mt-4 grid grid-cols-1 gap-x-6 gap-y-3 sm:grid-cols-6">
            <div class="sm:col-span-4">
                <div class="flex items-center">
                    <label for="petition-name" class="block text-sm font-medium leading-6 text-slate-900 dark:text-slate-200">Petition name</label><span class="ml-1 text-red-500">*</span>
                </div>
                <input type="text" name="petition-name" id="petition-name" v-model="petition.name" class="block w-full rounded-md border-0 py-1.5 text-slate-900 shadow-sm dark:shadow ring-1 ring-inset ring-gray-300 placeholder:text-slate-400 focus:ring-2 focus:ring-inset focus:ring-teal-400 sm:text-sm sm:leading-6 bg-white dark:bg-slate-100">
            </div>

            <div class="col-span-full">
                <div class="flex items-center">
                    <label for="description" class="block text-sm font-medium leading-6 text-slate-900 dark:text-slate-200">Description</label><span class="ml-1 text-red-500">*</span>
                </div>
                <textarea id="description" name="description" rows="3" v-model="petition.description" class="block w-full rounded-md border-0 py-1.5 text-slate-900 shadow-sm dark:shadow ring-1 ring-inset ring-gray-300 placeholder:text-slate-400 focus:ring-2 focus:ring-inset focus:ring-teal-400 sm:text-sm sm:leading-6 bg-white dark:bg-slate-100"></textarea>
                <p class="mt-1 text-sm leading-6 text-slate-500">Write a few sentences to describe what your petition is about.</p>
            </div>
        </div>

        <div class="pb-4">
            <div class="mt-6 grid grid-cols-1 gap-x-6 gap-y-3 sm:grid-cols-12">
                <div class="sm:col-span-3">
                    <div class="flex items-center">
                        <label for="target" class="block text-sm font-medium leading-6 text-slate-900 dark:text-slate-200">Signature target</label><span class="ml-1 text-red-500">*</span>
                    </div>
                    <input type="number" name="target" id="target" v-model="petition.signatureTarget" class="block w-full rounded-md border-0 py-1.5 text-slate-900 shadow-sm dark:shadow ring-1 ring-inset ring-gray-300 placeholder:text-slate-400 focus:ring-2 focus:ring-inset focus:ring-teal-400 sm:text-sm sm:leading-6 bg-white dark:bg-slate-100">
                </div>

                <div class="sm:col-span-9 md:col-span-12">
                    <label for="tags" class="block text-sm font-medium leading-6 text-slate-900 dark:text-slate-200">Tags</label>
                    <input type="text" name="tags" id="tags" v-model="tagsString" class="block w-full rounded-md border-0 py-1.5 text-slate-900 shadow-sm dark:shadow ring-1 ring-inset ring-gray-300 placeholder:text-slate-400 focus:ring-2 focus:ring-inset focus:ring-teal-400 sm:text-sm sm:leading-6 bg-white dark:bg-slate-100">
                <p class="mt-1 text-sm leading-6 text-slate-500">Every word (separated by a space character) will be added as a tag to this petition</p>
                </div>
            </div>
        </div>

        <input type="submit" class="w-full mb-6 rounded-md border-0 bg-teal-600 text-slate-200 focus:ring-2 focus:ring-teal-400 hover:bg-teal-500 text-sm font-medium py-1 px-3" value="Submit this petition">
    </form>
</template>

<script>
import PageStarter from '../PageStarter.vue';

export default {
    name: 'CreatePetitionView',
    components: {
        PageStarter,
    },

    data() {
        return {
            tagsString: "",
            petition: {
                name: "",
                description: "",
                signatureTarget: null,
                tags: [],
            },

            incorrectSubmit: false,
        };
    },

    methods: {
        uploadPetition() {
            this.petition.tags = this.tagsString.split(' ');

            this.incorrectSubmit = 
                this.petition.name === "" || 
                this.petition.description === "" ||
                this.petition.signatureTarget == null ||
                this.petition.signatureTarget <= 0
            ;
            if (this.incorrectSubmit) return;

            fetch("https://cloudmodule-414213.oa.r.appspot.com/_ah/api/tinyPetApi/ca-marche-sur-mon-pc/createPetition",
                { method: "POST", headers: { Authorization: localStorage.getItem("TinyPetGoogleToken") }, body: JSON.stringify(this.petition) }
            )
                .then(response => response.json())
                .then(data => this.$router.push({ name: 'petition', params: { petitionId: data.key.id }}))
            ;
        },
    },

    mounted() {
        if (localStorage.getItem("TinyPetGoogleToken") == null)
            this.$router.push({ name: "home" });
    }
}
</script>
