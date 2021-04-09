Vue.component("m-map", {
    template: `
    <yandex-map 
        v-bind:settings="settings"
        v-bind:coords="initCoords"
        v-bind:zoom="zoom"
        style="height: 800px; 100%"
        v-on:click="onClick"
    >
        <ymap-marker
            v-for="(manifestation, index) in manifestations.data"
            v-bind:key="manifestation.id"
            v-bind:markerId="manifestation.id"
            v-bind:coords="[manifestation.location.latitude, manifestation.location.longitude]"

            
            v-bind:balloon-template="balloonTemplate"
        />
    </yandex-map>
    `,

    props: {
        initCoords: {
            type: Array,
            required: true
        },
        zoom: {
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
            },

            initCoords: [44, 20],
        };
    },

    methods: {
    },

    mounted() {},

    destroyed() {}
});
