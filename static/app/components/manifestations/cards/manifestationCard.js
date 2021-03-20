Vue.component("manifestationCard", {
    template: `
    <div class="card">
        <img 
            v-bind:src="imageLocationToShow" 
            class="card-img-top" 
            alt="Image not found"
            v-on:error="showAlternateImage"
        >
        <div class="card-body d-flex flex-column">
            <div class="spaced">
                <h5 class="card-title">{{ manifestation.name }}</h5>
                <p class="card-title btn text-white" v-bind:style="{'background-color': statusColor}">{{ manifestation.status }}</p>
            </div>
            <div class="spaced">
                <h5 class="card-title" v-if="manifestation.status == 'INACTIVE'">Rating: {{ manifestation.avgRating }}</h5>
                <h5 class="card-title" v-else></h5>

                <h5 class="card-title">{{ manifestation.type }}</h5>
            </div>
            
            <h6>Regular ticket price: {{ manifestation.regularTicketPrice }} RSD</h6>
            <!-- <h6>Number of tickets left: {{ manifestation.numberOfTicketsLeft }}</h6>
            <h6>Total number of tickets: {{ manifestation.maxNumberOfTickets }}</h6>  -->
            
            <hr/>
            <p class="card-text description-scroll">{{ manifestation.description }}</p>
            <hr/>

            <div class="spaced mt-auto">
                <em style="margin-right: 10px;">{{ formattedAddress }}</em>
                <em>{{ manifestation.holdingDate }}</em>
            </div>
        </div>
        <div class="card-footer text-right">
            <button type="button" class="btn btn-success" v-on:click="redirectToManifestation">View page</button>
        </div>
    </div>
    `,

    props: {
        manifestation: Object
    },

    data: function() {
        return {
            imageLocationToShow: "",
            statusColor: "",

            StatusColors: Object.freeze({
                CREATED: "darkblue",     // DarkBlue
                REJECTED: "#FF8C00",    // DarkOrange
                ACTIVE: "#006400",      // DarkGreen
                INACTIVE: "#8B0000"     // DarkRed
            })
        };
    },

    methods: {
        showAlternateImage: function() {
            this.imageLocationToShow = "/images/no image 2.png"
        },

        redirectToManifestation: function() {
            // // NOTE: relies on current route
            // const lastIndex = this.$route.path.lastIndexOf("/");
            // const pathTo = this.$route.path.substring(0, lastIndex + 1) + this.manifestation.id;
            // this.$router.push({ path: pathTo });


            // NOTE: relies on roles
            let rolePath = "";
            if (this.$root.isAdmin())
                rolePath = "/admin";
            else if (this.$root.isSalesman())
                rolePath = "/salesman";
            else if (this.$root.isCustomer())
                rolePath = "/customer";

            
            const pathTo = `${rolePath}/manifestations/${this.manifestation.id}`;
            this.$router.push({ path: pathTo });
        }
    },

    computed: {
        formattedAddress() {
            const address = this.manifestation.location.address;
            return address.street + " " + address.number + ", " + address.city + ", " + address.postalCode;
        }
    },

    mounted() {
        this.imageLocationToShow = `data:image/${this.manifestation.imageType};base64, ${this.manifestation.imageBase64}`;

        this.statusColor = this.StatusColors[this.manifestation.status];
    },

    destroyed() {}
});