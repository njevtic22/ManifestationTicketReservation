Vue.component("all-marker", {
    template: `
    <ymap-marker
        v-bind:markerId="manifestation.id"
        v-bind:coords="[manifestation.location.latitude, manifestation.location.longitude]""

        v-bind:balloon-template="balloonTemplate"
        
        v-on:balloonopen="bindListener"
        v-on:balloonclose="unbindListener"
    />
    `,

    props: {
        manifestation: {
            type: Object,
            required: true
        }
    },

    data: function() {
        return {
            imageToShow: "images/no image 2.png",

            
            statusColor: "",

            StatusColors: Object.freeze({
                CREATED: "#0000A0",     // DarkBlue
                REJECTED: "#FF8C00",    // DarkOrange
                ACTIVE: "#006400",      // DarkGreen
                INACTIVE: "#8B0000"     // DarkRed
            })
        };
    },

    computed: {
        balloonTemplate() {
            // Not working properly vith vue directives (v-if, v-bind)
            return `
            <button id="${this.manifestation.id.toString() + "btn"}" class="btn btn-primary btn-block">View details</button>
            <br/>
            <div class="card border-0">
            
                <img src="${this.imageToShow}" style="width: 100%;">
                <br/>
                <br/>
                <div class="d-flex justify-content-between">
                    <h5 class="card-title">${this.manifestation.name}</h5>
                    <p class="card-title btn text-white" style="background-color: ${this.statusColor};">${this.manifestation.status}</p>
                </div>
                <div class="d-flex justify-content-between">
                    <h5 class="card-title">${this.rating}</h5>

                    <h5 class="card-title">${this.manifestation.type}</h5>
                </div>
                <h6>Regular ticket price: ${this.manifestation.regularTicketPrice} RSD</h6>

                
                <hr/>
                <p class="card-text scroll scroll-invisible description-scroll">${this.manifestation.description}</p>
                <hr/>

                <div class="d-flex justify-content-between mt-auto">
                    <em style="margin-right: 10px;">${this.formattedAddress}</em>
                    <em>${this.manifestation.holdingDate}</em>
                </div>

            </div>
            <br/>
            <button id="${this.manifestation.id.toString() + "btn2"}" class="btn btn-primary btn-block">View details</button>
            `;
        },
        
        formattedAddress() {
            const address = this.manifestation.location.address;
            return address.street + " " + address.number + ", " + address.city + ", " + address.postalCode;
        },

        rating() {
            if (this.manifestation.status === "INACTIVE") {
                return "Rating: " + this.manifestation.avgRating;   
            } else {
                return " ";
            }
        }
    },

    methods: {
        bindListener() {
            document.getElementById(`${this.manifestation.id.toString() + "btn"}`)
                .addEventListener('click', this.redirectToManifestation);
                
            document.getElementById(`${this.manifestation.id.toString() + "btn2"}`)
                .addEventListener('click', this.redirectToManifestation);
        },
        unbindListener() {
            document.getElementById(`${this.manifestation.id.toString() + "btn"}`)
                .removeEventListener('click', this.redirectToManifestation);

            document.getElementById(`${this.manifestation.id.toString() + "btn2"}`)
                .removeEventListener('click', this.redirectToManifestation);
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

    created() {
        this.imageToShow = `data:image/${this.manifestation.imageType};base64, ${this.manifestation.imageBase64}`;
        this.statusColor = this.StatusColors[this.manifestation.status];
    },

    destroyed() {}
});
