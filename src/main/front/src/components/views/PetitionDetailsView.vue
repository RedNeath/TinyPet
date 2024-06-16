<template>
    <PageStarter 
        :title="petition.properties.name" 
        :breadcrumbs="[{ label: 'Home', path: '/' }, { label: petition.properties.name, path: '/petition/' + petition.key.id }]" 
        :actions="[{ label: 'Sign', trigger: signPetition }]"
    />
</template>

<script>
import PageStarter from '../PageStarter.vue';

export default {
    name: 'PetitionDetailsView',
    components: {
        PageStarter,
    },

    data() {
        return {
            petition: { properties: { name: "Loading..." }, key: { id: 0 } },
        }
    },

    methods: {
        signPetition() {
            fetch("https://cloudmodule-414213.oa.r.appspot.com/_ah/api/tinyPetApi/ca-marche-sur-mon-pc/signPetition/" + this.petition.key.id,
                { method: "POST", headers: { Email: "test@example.com" } }
            );
        }
    },

    mounted() {
        // Replace with a fetch
        this.petition = {
            "key": {
                "kind": "Petition",
                "appId": "o~cloudmodule-414213",
                "id": "5658646574792704",
                "complete": true,
                "namespace": ""
            },
            "kind": "Petition",
            "appId": "o~cloudmodule-414213",
            "namespace": "",
            "properties": {
                "end_date": "2024-12-15T14:32:19.064Z",
                "author": "test@example.com",
                "signature_target": "1500",
                "name": "Reduce plastic waste",
                "description": "Plastic waste is a major problem for our planet. Let's reduce our use of plastic and promote sustainable alternatives.",
                "beg_date": "2024-06-15T14:32:19.125Z",
                "tags": [
                    "plastic waste",
                    "sustainability",
                    "environment"
                ]
            }
        };
    }
}
</script>
