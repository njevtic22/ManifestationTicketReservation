Vue.component("manifestation", {
    template: `
    <div>
        <div class="form-row border-0 manifestation-details shadow-lg">
            <div class="row" v-if="belognsToSalesman">
                <div class="col text-right">
                    <button class="btn btn-primary" v-on:click="changeDetails">Change details</button>
                    <button class="btn btn-primary" v-on:click="changeLocation">Change location</button>
                </div>
                <br/>
                <br/>
            </div>
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
                    <p class="scroll scroll-invisible manifestation-description-scroll">{{ manifestation.description  }}</p>
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
                    <br/>
                    <br/>
                    <br/>
                    
                    <div class="d-flex justify-content-center" v-if="location.latitude == null || location.longitude == null">
                        <div class="spinner-grow text-secondary" role="status" style="width: 3rem; height: 3rem;">
                            <span class="sr-only">Loading...</span>
                        </div>
                    </div>

                    <div v-else>
                        <m-map
                            style="height: 500px; width: 100%;"
                            v-bind:zoom="15"
                            v-bind:location="[location.latitude, location.longitude]"
                        >
                        </m-map>
                        <!-- v-bind:location="[location.latitude, location.longitude]" maybe not reactive in map -->
                    </div>
                </div>
            </div>
        </div>

        <div class="form-row" style="margin-top: 20px;" v-if="reviews.data.length !== 0">
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

        <changeManifestationModal 
            id="changeManifestationModal" 
            v-bind:manifestation="manifestation"
        ></changeManifestationModal>
        <changeLocationModal 
            id="changeLocationModal"
            v-bind:location="location"
            v-bind:address="address"
        ></changeLocationModal>
        
        <manifestationService ref="manifestationService"></manifestationService>
    </div>
    `,

    data: function() {
        return {
            belognsToSalesman: false,

            imageLocationToShow: "",
            manifestation: { 
                id: 0, 
                name: "",
                numberOfTicketsLeft: 0,
                maxNumberOfTickets: 0,
                regularTicketPrice: 0,
                holdingDate: "",
                description: "",
                status: "CREATED",
                type: "CONCERT",

                avgRating: 0,

                location: {
                    id: 0, 
                    longitude: null, 
                    latitude: null, 
                    address: {
                        street: "", 
                        number: 0, 
                        city: "", 
                        postalCode: ""
                    } 
                }, 

                imageBase64: "",
                imageType: "", 

                reviews: []
            },
            

            location: {
                id: 0,
                latitude: 0,
                longitude: 0,
                address: {
                    street: "", 
                    number: 0, 
                    city: "", 
                    postalCode: ""
                } 
            },
            address: {
                street: "", 
                number: 0, 
                city: "", 
                postalCode: ""
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
        },

        getBelongsToSalesman: function() {
            if (this.$root.isSalesman()) {
                const successCallback = (response) => {
                    this.belognsToSalesman = response.data;
                };
                const errorCallback = (error) => {
                    this.$root.defaultCatchError(error);
                };
                this.$refs.manifestationService.belognsToSalesman(
                    this.$route.params.id,
                    successCallback,
                    errorCallback
                );
            } else {
                this.belognsToSalesman = false;
            }
        },

        changeDetails: function() {
            $("#changeManifestationModal").modal("show");
        },
        
        changeLocation: function() {
            $("#changeLocationModal").modal("show");
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

        this.getBelongsToSalesman();
    },

    destroyed() {}
});
