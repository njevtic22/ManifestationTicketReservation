Vue.component("all-map", {
    template: `
    <yandex-map 
        v-bind:settings="settings"
        v-bind:coords="centerCords"
        v-bind:zoom="zoom"
        v-on:click="onClick"
    >
        <all-marker
            v-for="(manifestation, index) in manifestations"
            v-bind:key="manifestation.id"
            v-bind:manifestation="manifestation"
        >
        </all-marker>
    </yandex-map>
    `,

    props: {
        manifestations: {
            type: Array,
            required: true
        },
        zoom: {
            type: Number,
            required: true
        }
    },

    computed: {
        centerCords() {
            return this.manifestations.length !== 0 ? [this.manifestations[0].location.latitude, this.manifestations[0].location.longitude] : [44, 20];
        },
    },

    data: function() {
        return {
            settings: {
                apiKey: 'daf8ca4b-3c4e-4396-9bff-8c6b22f7e69a',
                lang: 'en_US',
                coordorder: 'latlong',
                version: '2.1'
            }
        };
    },

    methods: {
        onClick: function(e) {
            console.log(e);
            console.log(e.get('coords'));
        },
    },

    mounted() {
        // https://vue-yandex-maps.github.io/en/examples/#move-the-marker-by-click
    },

    destroyed() {}
});
