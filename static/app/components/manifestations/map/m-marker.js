Vue.component("m-marker", {
    template: `
    <ymap-marker
        v-bind:markerId="markerId"
        v-bind:coords="location"
    />
    `,

    props: {
        location: {
            type: Array,
            required: true
        },
        markerId: {
            type: Number,
            required: true,
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
