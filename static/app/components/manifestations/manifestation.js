Vue.component("manifestation", {
    template: `
    <div>
        <div class="form-row border-0 manifestation-details shadow-lg">
            <div class="row">
                <div class="col d-flex flex-column">
                    <h3>{{ manifestation.name }}</h3>

                    <div class="d-flex justify-content-between">
                        <h5>{{ manifestation.type }}</h5>
                        <div>
                        <p 
                            class="card-title btn text-white"
                            style="cursor: default;"
                            v-bind:style="{'background-color': statusColor}"
                        >
                            {{ manifestation.status }}
                        </p>
                        </div>
                    </div>
                    
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
                    <p class="manifestation-description-scroll">{{ manifestation.description  }}</p>
                    <hr/>

                    
                    <div class="d-flex justify-content-between mt-auto">
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

        <div class="form-row" style="margin-top: 20px;">
            <div class="col-sm card border-0 manifestation-details shadow-lg" style="margin-left: 10px; padding: 3%;">
                <div class="d-flex justify-content-between">
                    <h1>Average rating: {{ manifestation.avgRating }}</h1>
                    <button 
                        class="btn btn-primary" 
                        v-if="$root.isCustomer() && manifestation.status === 'INACTIVE'"
                    >
                        Add review
                    </button>
                </div>
                <br/>
                <div class="d-flex justify-content-between">
                    <pageSizeSelect
                        class="d-flex justify-content-center"

                        name="sizeInput"
                        v-bind:value="reviewSizeStr"
                        v-bind:options="reivewSizeOptions"
                        v-bind:page="reviewPage"
                        v-bind:size="reviewSize"
                        v-bind:currentDataSize="reviews.data.length"
                        v-bind:totalNumberOfResults="reviews.totalNumberOfResults"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    >
                    </pageSizeSelect>

                    <pagination
                        class="d-flex justify-content-center"

                        v-bind:currentPage="reviewPage"
                        v-bind:hasPrevious="reviews.hasPreviousPage"
                        v-bind:hasNext="reviews.hasNextPage"

                        v-on:previous="previousPage"
                        v-on:next="nextPage"
                        v-on:to="toPage($event)"
                    >
                    </pagination>
                </div>
                
                <div>
                    <div v-for="x in reviews.data">
                        {{ x }} TODO: show Review
                    </div>
                </div>

                <br/>
                <div class="d-flex justify-content-between">
                    <pageSizeSelect
                        class="d-flex justify-content-center"

                        name="sizeInput"
                        v-bind:value="reviewSizeStr"
                        v-bind:options="reivewSizeOptions"
                        v-bind:page="reviewPage"
                        v-bind:size="reviewSize"
                        v-bind:currentDataSize="reviews.data.length"
                        v-bind:totalNumberOfResults="reviews.totalNumberOfResults"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    >
                    </pageSizeSelect>

                    <pagination
                        class="d-flex justify-content-center"

                        v-bind:currentPage="reviewPage"
                        v-bind:hasPrevious="reviews.hasPreviousPage"
                        v-bind:hasNext="reviews.hasNextPage"

                        v-on:previous="previousPage"
                        v-on:next="nextPage"
                        v-on:to="toPage($event)"
                    >
                    </pagination>
                </div>
            </div>
        </div>
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
            }),

            reviewPage: 0,
            reviewSize: 5,
            reviewSizeStr: "5",
            reivewSizeOptions: [
                "5",
                "10",
                "50",
                "All"
            ],

            reviews: {
                data: [
                    0, 
                    1, 
                    2, 
                    3, 
                    4
                ],
                totalNumberOfResults: 10,
                hasPreviousPage: true,
                hasNextPage: true
            }
        };
    },

    methods: {
        showAlternateImage: function() {
            this.imageLocationToShow = "/images/no image 2.png";
        },

        changeSize: function(event) {
            this.reviewPage = 0;
            this.reviewSizeStr = event;
            if (event === "All") {
                this.reviewSize = Infinity;
            } else {
                this.reviewSize = Number(event);
            }
            this.getReviews();
        },

        previousPage: function() {
            this.reviewPage--;
            this.getReviews();
        },

        nextPage: function() {
            this.reviewPage++;
            this.getReviews();
        },

        toPage: function(to) {
            this.page = to;
            this.getReviews();
        },

        getReviews: function() {
            console.log("Implement this");
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
        this.getReviews();
    },

    destroyed() {}
});
