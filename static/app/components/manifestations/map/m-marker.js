Vue.component("m-marker", {
    template: `
    <ymap-marker
        v-bind:markerId="manifestation.id"
        v-bind:coords="[manifestation.location.latitude, manifestation.location.longitude]""
    />
    `,

    props: {
        manifestation: {
            type: Object,
            required: true
        }
    },

    data: function() {
        return {};
    },

    methods: {
        
    },

    mounted() {},

    destroyed() {}
});
