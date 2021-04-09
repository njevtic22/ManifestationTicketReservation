Vue.component("m-map", {
    template: `
    <yandex-map 
        v-bind:settings="settings"
        v-bind:coords="location"
        v-bind:zoom="zoom"
    >
        <m-marker
            v-bind:markerId="123"
            v-bind:location="location"
        >
        </m-marker>
    </yandex-map>
    `,

    props: {
        location: {
            type: Array,
            required: true,
        },
        zoom: {
            type: Number,
            required: true
        }
    },

    data: function() {
        return {
            settings: {
                apiKey: 'daf8ca4b-3c4e-4396-9bff-8c6b22f7e69a',
                lang: 'en-US',
                coordorder: 'latlong',
                version: '2.1'
            }
        };
    },

    methods: {
    },

    mounted() {},

    destroyed() {}
});
