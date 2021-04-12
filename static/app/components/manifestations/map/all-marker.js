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
            // Not working properly vith vue directives (v-if, v-bind ...)
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
                <p 
                    class="
                        scroll 
                        scroll-invisible 
                        description-scroll
                        text-wrap
                        text-break
                        "
                >
                    ${this.manifestation.description}
                </p>
                <hr/>

                <div class="d-flex justify-content-between mt-auto">
                    <em style="margin-right: 10px;">${this.formattedAddress}</em>
                    <em>${this.manifestation.holdingDate}</em>
                </div>

            </div>
            <br/>
            <button id="${this.manifestation.id.toString() + "btn2"}" class="btn btn-primary btn-block">View details</button>
            <button
                id="deleteManifestationBtn" 
                class="btn btn-danger btn-block"
            >
                Delete manifestation
            </button>
            `;
            // Not working properly vith vue directives (v-if, v-bind ...)
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

            // remove delete button if user is not admin
            if (!this.$root.isAdmin()) {
                $("#deleteManifestationBtn").remove();
            } else {
                document.getElementById("deleteManifestationBtn").addEventListener('click', this.deleteManifestation);
            }
        },
        unbindListener() {
            /*
            
            // Remove the listener if button exist
                let button = document.getElementById(`${this.manifestation.id.toString() + "btn"}`);
                if (button != null) {
                    button.removeEventListener('click', this.redirectToManifestation);
                }
                
                // Remove the listener if button exist
                button = document.getElementById(`${this.manifestation.id.toString() + "btn2"}`)
                if (button != null) {
                    button.removeEventListener('click', this.redirectToManifestation);
                }


                // Remove the listener if button exist
                button = document.getElementById("deleteManifestationBtn");
                if (button != null) {
                    button.removeEventListener('click', this.deleteManifestation);
                }

            */

            try {
                // throws error if button does not exist, and it does not exist when baloon is closed 
                document.getElementById(`${this.manifestation.id.toString() + "btn"}`)
                    .removeEventListener('click', this.redirectToManifestation);
    
                document.getElementById(`${this.manifestation.id.toString() + "btn2"}`)
                    .removeEventListener('click', this.redirectToManifestation);
                    
                document.getElementById("deleteManifestationBtn").
                    removeEventListener('click', this.deleteManifestation);

            } catch(error) {
                // console.log(error);
            }
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
        },

        deleteManifestation: function() {
            this.$emit('deleteManifestation', this.manifestation.id);
        }
    },

    created() {
        this.imageToShow = `data:image/${this.manifestation.imageType};base64, ${this.manifestation.imageBase64}`;
        this.statusColor = this.StatusColors[this.manifestation.status];
    },

    destroyed() {}
});
