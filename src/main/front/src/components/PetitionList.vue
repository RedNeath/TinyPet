<template>
    <div id="petition-container">
        <div v-for="petition in petitions" :key="petition.key">
            <div class="flex md:hidden">
                <span v-for="tag in petition.properties.tags" :key="tag">{{ tag }}</span>
            </div>
            <div class="flex justify-between">
                <div>
                    <span class="text-xl">{{ petition.properties.name }}</span>
                    <div class="hidden md:flex">
                        <span v-for="tag in petition.properties.tags" :key="tag">{{ tag }}</span>
                    </div>
                </div>
                <button>Sign</button>
            </div>

            <p>{{ petition.properties.description }}</p>
            <span class="md:hidden">{{ "Ends on: " + petition.properties.end_date }}</span>
            <div class="hidden md:flex justify-between">
                <span>{{ petition.properties.beg_date + " - " + petition.properties.end_date }}</span>
                <span>{{ "Target: " + petition.properties.signature_target }}</span>
            </div>

            <button class="md:hidden w-full">See more -></button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'PetitionList',

    props: {
        petitions: Array
    },
}
</script>
