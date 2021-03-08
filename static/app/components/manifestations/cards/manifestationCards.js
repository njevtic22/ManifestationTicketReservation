Vue.component("manifestationCards", {
    template: `
    <div class="row row-cols-1 row-cols-3">
        <div class="col mb-4" v-for="man in manifestations">
            <manifestationCard
                class="h-100 shadow-lg"
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
