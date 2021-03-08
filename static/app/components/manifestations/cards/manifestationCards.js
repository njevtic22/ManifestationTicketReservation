Vue.component("manifestationCards", {
    template: `
    <div>
        <div v-for="man in manifestations">
            <manifestationCard 
                v-bind:manifestation="man"
            >
            </manifestationCard>
        </div>
    </div>
    `,

    props: {
        manifestations: Array
    },

    data: function() {
        return {};
    },

    methods: {
    },

    mounted() {},

    destroyed() {}
});
