Vue.component("manifestation", {
    template: `
    <div class="manifestation-details">
        <div class="row">
            <div class="col d-flex flex-column">
                <h3>{{ manifestation.name }}</h3>

                <div class="spaced">
                    <h5>{{ manifestation.type }}</h5>
                    <p class="card-title btn text-white" v-bind:style="{'background-color': statusColor}">{{ manifestation.status }}</p>
                </div>

                <em>{{ manifestation.holdingDate }}</em>
                <br/>
                <em>{{ formattedAddress }}</em>
                
                <br/>
                <br/>
                <div class='row' style="margin-left: 1px">
                    <div class="col-md-6"> 
                        <div class="row">Total number of tickets:</div>
                        <div class="row">Tickets left:</div>
                        <div class="row">Regular ticket price:</div>
                    </div>
                    <div class="col-md-4"> 
                        <div class="row">{{ manifestation.maxNumberOfTickets }}</div>
                        <div class="row">{{ manifestation.numberOfTicketsLeft }}</div>
                        <div class="row">{{ manifestation.regularTicketPrice }} RSD</div>
                    </div>
                </div>

                <hr/>
                <p class="description-scroll">{{ manifestation.description }}</p>
                <hr/>

                
                <div class="spaced mt-auto">
                    <em>{{ formattedAddress }}</em>
                    <em>{{ manifestation.holdingDate }}</em>
                </div>
            </div>
            <div class="col manifestation-image">
                <img 
                    v-bind:src="imageLocationToShow"
                    alt="Image not found"
                    v-on:error="showAlternateImage"
                >
            </div>
        </div>

        <manifestationService ref="manifestationService"></manifestationService>
    </div>
    `,

    data: function() {
        return {
            imageLocationToShow: "",
            manifestation: {

            },
            
            /*
            
            { 
                "id": 7, 
                "name": "Concert 7: Estelle Leonard", ---
                "numberOfTicketsLeft": 20, ---
                "maxNumberOfTickets": 20, ---
                "regularTicketPrice": 2000, ---
                "holdingDate": "07.04.2021. 08:00:00",--- 
                "description": "", ---
                "status": "CREATED", ---
                "type": "CONCERT", ---

                "avgRating": 0, <------ THIS

                "location": {  <------ THIS MAP
                    "id": 7, 
                    "longitude": 45.2358711, 
                    "latitude": 19.8348293, 
                    "address": {
                        "street": "Bulevar despota Stefana", 
                        "number": 42, 
                        "city": "Novi Sad", 
                        "postalCode": "21000"
                    } 
                }, 

                "imageBase64": "", ---
                "imageType": "jpg", 

                "reviews": [] <------ THIS
            }       
            */

            location: {

            },
            address: {

            },

            
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
        showAlternateImage: function() {
            this.imageLocationToShow = "/images/no image 2.png"
        },

        getManifestation: function(manifestationId) {
            const successCallback = (response) => {
                this.manifestation = response.data;
                this.location = response.data.location;
                this.address = response.data.location.address;
                this.statusColor = this.StatusColors[this.manifestation.status];

                this.imageLocationToShow = `data:image/${this.manifestation.imageType};base64, ${this.manifestation.imageBase64}`;
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.getManifestation(manifestationId, successCallback, errorCallback);
        }
    },

    computed: {
        formattedAddress() {
            const address = this.address;
            return address.street + " " + address.number + ", " + address.city + ", " + address.postalCode;
        }
    },

    mounted() {
        const id = this.$route.params.id;
        this.getManifestation(id);
    },

    destroyed() {}
});
