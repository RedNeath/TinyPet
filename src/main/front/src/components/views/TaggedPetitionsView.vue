<template>
    <PageStarter title="Matching petitions" :breadcrumbs="[{ label: 'Home', path: '/' }, { label: 'Petitions by tag', path: '/search/' + this.$route.params.tags }]" />
    <PetitionList :petitions="petitions" />
</template>

<script>
import PageStarter from '../PageStarter.vue';
import PetitionList from '../PetitionList.vue';

export default {
    name: 'TaggedPetitionsView',
    components: {
        PageStarter,
        PetitionList,
    },

    watch: {
        $route(to, from) {
            if (to.params.tags !== from.params.tags) {
                this.fetchData(to.params.tags);
            }
        }
    },

    methods: {
        fetchData(searchTags) {
            fetch("https://cloudmodule-414213.oa.r.appspot.com/_ah/api/tinyPetApi/ca-marche-sur-mon-pc/petitionsWithTags?tags=" + searchTags)
            .then(response => response.json())
            .then(payload => this.petitions = payload.items);
        }
    },

    data() {
        return {
            petitions: [],
        };
    },

    mounted() {
        this.fetchData(this.$route.params.tags)
    }
}
</script>
