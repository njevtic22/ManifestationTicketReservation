Vue.component("manifestationCard", {
    template: `
    <div class="card border-dark">
        <img 
            v-bind:src="imageLocationToShow" 
            class="card-img-top" 
            alt="Image not found"
            v-on:error="showAlternateImage"
        >
        <div class="card-body">
            <h5 class="card-title">{{ manifestation.name }}</h5>
            <h5 class="card-title">{{ manifestation.holdingDate }}</h5>
            <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
        </div>
        <div class="card-footer text-right">
            <button type="button" class="btn btn-success">Button</button>
        </div>
    </div>
    `,

    props: {
        manifestation: Object
    },

    data: function() {
        return {
            imageLocationToShow: ""
        };
    },

    methods: {
        showAlternateImage: function() {
            this.imageLocationToShow = "/images/no image 2.png"
        }
    },

    mounted() {
        this.imageLocationToShow = this.manifestation.imageLocation;
    },

    destroyed() {}
});
