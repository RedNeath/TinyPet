<template>
    <PageStarter title="Top 100 most recent petitions" :breadcrumbs="[{ label: 'Home', path: '/' }]" />
    <PetitionList :petitions="petitions" />
</template>

<script>
import PageStarter from '../PageStarter.vue';
import PetitionList from '../PetitionList.vue';

export default {
    name: 'RecentPetitionsView',
    components: {
        PageStarter,
        PetitionList,
    },

    data() {
        return {
            petitions: [],
        };
    },

    mounted() {
        fetch("https://cloudmodule-414213.oa.r.appspot.com/_ah/api/tinyPetApi/ca-marche-sur-mon-pc/recentPetitions")
            .then(response => response.json())
            .then(payload => this.petitions = payload.items);
    }
}
</script>
