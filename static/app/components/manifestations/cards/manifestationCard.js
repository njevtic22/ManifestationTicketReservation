Vue.component("manifestationCard", {
    template: `
    <div 
        class="card border-0"
        v-bind:id="manifestation.id"
        v-on:mouseenter="addShadow"
        v-on:mouseleave="removeShadow"
        v-on:click="redirectToManifestation"
    >
        <img 
            v-bind:src="imageLocationToShow" 
            class="card-img-top" 
            alt="Image not found"
            v-on:error="showAlternateImage"
        >
        <div class="card-body d-flex flex-column">
            <div class="d-flex justify-content-between">
                <h5 class="card-title">{{ manifestation.name }}</h5>
                <p class="card-title btn text-white" v-bind:style="{'background-color': statusColor}">{{ manifestation.status }}</p>
            </div>
            <div class="d-flex justify-content-between">
                <h5 class="card-title" v-if="manifestation.status == 'INACTIVE'">Rating: {{ manifestation.avgRating }}</h5>
                <h5 class="card-title" v-else></h5>

                <h5 class="card-title">{{ manifestation.type }}</h5>
            </div>
            
            <h6>Regular ticket price: {{ manifestation.regularTicketPrice }} RSD</h6>
            <!-- <h6>Number of tickets left: {{ manifestation.numberOfTicketsLeft }}</h6>
            <h6>Total number of tickets: {{ manifestation.maxNumberOfTickets }}</h6>  -->
            
            <hr/>
            <p class="card-text scroll scroll-invisible description-scroll">{{ manifestation.description }}</p>
            <hr/>

            <div class="d-flex justify-content-between mt-auto">
                <em style="margin-right: 10px;">{{ formattedAddress }}</em>
                <em>{{ manifestation.holdingDate }}</em>
            </div>
        </div>
        <!--
        <div class="card-footer text-right">
            <button type="button" class="btn btn-success" v-on:click="redirectToManifestation">View details</button>
        </div> -->
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
                CREATED: "#0000A0",     // DarkBlue
                REJECTED: "#FF8C00",    // DarkOrange
                ACTIVE: "#006400",      // DarkGreen
                INACTIVE: "#8B0000"     // DarkRed
            })
        };
    },

    methods: {
        addShadow: function() {
            $(`#${this.manifestation.id}`).addClass('shadow-lg').css('cursor', 'pointer'); 
        },

        removeShadow: function() {
            $(`#${this.manifestation.id}`).removeClass('shadow-lg');
        },

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
