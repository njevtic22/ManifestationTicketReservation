Vue.component("add-map", {
    template: `
    <yandex-map 
        v-bind:settings="settings"
        v-bind:coords="[newManifestation.latitude, newManifestation.longitude]"
        v-bind:zoom="10" 
        v-on:click="moveMarker($event)"
    >
        <ymap-marker 
            v-bind:coords="[newManifestation.latitude, newManifestation.longitude]" 
            marker-id="123" 
        />
    </yandex-map>
    `,

    props: {
        newManifestation: {
            type: Object,
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
        // https://vue-yandex-maps.github.io/en/examples/#move-the-marker-by-click
    },

    destroyed() {}
});
