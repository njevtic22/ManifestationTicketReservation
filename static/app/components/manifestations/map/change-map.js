Vue.component("change-map", {
    template: `
    <yandex-map 
        v-bind:settings="settings"
        v-bind:coords="location"
        v-bind:zoom="zoom"
        
        v-on:click="moveMarker($event)"
    >
        <ymap-marker 
            marker-id="123" 
            v-bind:coords="location"
        />
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
            coordinates: [],
            settings: {
                apiKey: 'daf8ca4b-3c4e-4396-9bff-8c6b22f7e69a',
                lang: 'en_US',
                coordorder: 'latlong',
                version: '2.1'
            }
        };
    },

    methods: {
        moveMarker: function(e) {
            this.coordinates = e.get('coords');

            const coordiantes = {
                latitude:  this.coordinates[0],
                longitude: this.coordinates[1]
            }

            this.$emit("coordsChosen", coordiantes);
        }
    },

    mounted() {
        // this.coordinates = this.initCoords;
    },

    destroyed() {}
});
