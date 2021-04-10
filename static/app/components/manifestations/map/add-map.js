Vue.component("add-map", {
    template: `
    <yandex-map 
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
            coordinates: []
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
        this.coordinates = this.initCoords;
    },

    destroyed() {}
});
