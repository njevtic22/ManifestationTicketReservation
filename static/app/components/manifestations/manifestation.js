Vue.component("manifestation", {
    template: `
    <div>
        <div class="form-row border-0 manifestation-details shadow-lg">
            <div 
                class="row" 
                v-if="$root.isSalesman() && belognsToSalesman && (manifestation.status == 'CREATED' || manifestation.status == 'ACTIVE')"
            >
                <div class="col text-right">
                    <button 
                        class="btn btn-primary"
                        v-on:click="changeDetails"
                    >
                        Change details
                    </button>
                    <button 
                        class="btn btn-primary" 
                        v-on:click="changeLocation"
                    >
                        Change location
                    </button>
                    <button 
                        class="btn btn-primary"
                        data-toggle="modal"
                        data-target="#addTicketsModal"
                    >
                        Add Tickets
                    </button>
                </div>
                <br/>
                <br/>
            </div>
            <div class="row" v-if="$root.isAdmin()">
                <manifestationOptions
                    class="btn"
                    v-bind:manifestation="manifestation"
                    
                    v-on:end="endManifestation"
                    v-on:reject="rejectManifestation"
                    v-on:approve="approveManifestation"
                    v-on:deleteManifestation="deleteManifestation"
                >
                </manifestationOptions>
                <br/>
                <br/>
            </div>
            <div class="row" v-if="$root.isCustomer() && manifestation.status == 'ACTIVE' && !manifestation.isSoldOut">
                <div class="col text-right">
                    <button 
                        class="btn btn-primary"
                        v-on:click="showReserveTicketsModal"
                    >
                        Reserve tickets
                    </button>
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
                        Max number of tickets: {{ manifestation.maxNumberOfTickets }}
                    </div>
                    <div class='row' style="margin-left: 1px">
                        <div class="col-md-4"> 
                            <div class="row"><br/></div>
                            <div class="row">Regular tickets:</div>
                            <div class="row">Fan pit tickets:</div>
                            <div class="row">VIP tickets:</div>
            
                        </div>
                        <div class="col-md-4"> 
                            <div class="row">Left</div>
                            <div class="row">{{ manifestation.numberOfRegularTicketsLeft }}</div>
                            <div class="row">{{ manifestation.numberOfFanTicketsLeft }}</div>
                            <div class="row">{{ manifestation.numberOfVipTicketsLeft }}</div>
            
                        </div>
                        <div class="col-md-4">
                            <div class="row">Price</div>
                            <div class="row">{{ manifestation.regularTicketPrice }} RSD</div>
                            <div class="row">{{ manifestation.fanTicketPrice }} RSD</div>
                            <div class="row">{{ manifestation.vipTicketPrice }} RSD</div>

                        </div>
                    </div>

                    <hr/>
                    <p 
                        class="
                            scroll 
                            scroll-invisible 
                            manifestation-description-scroll
                            text-wrap
                            text-break
                            "
                    >
                        {{ manifestation.description  }}
                    </p>
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

        <div 
            class="form-row" 
            style="margin-top: 20px;" 
            v-if="manifestation.hasEnded && manifestation.status == 'INACTIVE'"
        >
            <div class="col-sm card border-0 manifestation-details shadow-lg" style="margin-left: 10px; padding: 3%;">
                
                <div class="d-flex justify-content-between">
                    <h1>Average rating: {{ parseRating }}</h1>
                    <button 
                        class="btn btn-primary" 
                        v-if="$root.isCustomer() && manifestation.status === 'INACTIVE' && canLeaveReview"

                        data-toggle="modal"
                        data-target="#addReviewModal"
                    >
                        Add review
                    </button>
                    <selectInput
                        name="reviewType"
                        labelText="Review type"
                        v-bind:value="reviewTypeValueComputed"
                        v-bind:options="reviewTypeOptionscomputed"
                        class="form-group"
                        style="width: 20%;"
                        required

                        
                        v-if="$root.isAdmin() || $root.isSalesman()"
                        v-on:select="changeReviewType($event)"
                    >
                    </selectInput>
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

                <div v-if="reviews.data.length === 0">
                    <h3>No reivews</h3>
                </div>
                <div v-else>
                    <div v-for="oneReview in reviews.data">
                        <review
                            v-bind:review="oneReview"
                            v-bind:belognsToSalesman="belognsToSalesman"

                            v-on:approveReview="approveReview($event)"
                            v-on:rejectReview="rejectReview($event)"
                            v-on:deleteReview="deleteReview($event)"
                        >
                        </review>
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
            ref="changeManifestationModal"

            v-on:manifestationUpdated="getManifestation($route.params.id)"
        ></changeManifestationModal>
        <changeLocationModal 
            id="changeLocationModal"
            ref="changeLocationModal"
            
            v-on:locationUpdated="getManifestation($route.params.id)"
        ></changeLocationModal>
        
        <addTicketsModal
            id="addTicketsModal"
            ref="addTicketsModal"

            v-bind:manifestationId="manifestation.id"
            v-bind:manifestationName="manifestation.name"
            v-on:addedTickets="getManifestation($route.params.id)"
        ></addTicketsModal>

        <reserveTicketsModal
            id="reserveTicketsModal"
            ref="reserveTicketsModal"

            v-bind:manifestationId="manifestation.id"
            v-bind:manifestationName="manifestation.name"

            v-bind:regularPrice="manifestation.regularTicketPrice"
            v-bind:fanPrice="manifestation.fanTicketPrice"
            v-bind:vipPrice="manifestation.vipTicketPrice"

            v-bind:regularLeft="manifestation.numberOfRegularTicketsLeft"
            v-bind:fanLeft="manifestation.numberOfFanTicketsLeft"
            v-bind:vipLeft="manifestation.numberOfVipTicketsLeft"

            v-on:ticketsReserved="getManifestation($route.params.id)"
        ></reserveTicketsModal>

        <addReviewModal
            id="addReviewModal"
            ref="addReviewModal"

            v-on:addReview="addReview($event)"
        >
        </addReviewModal>

        <reviewService ref="reviewService"></reviewService>
        <manifestationService ref="manifestationService"></manifestationService>
    </div>
    `,

    data: function() {
        return {
            belognsToSalesman: false,
            canLeaveReview: false,

            imageLocationToShow: "",
            manifestation: { 
                id: 0, 
                name: "",
                numberOfTicketsLeft: 0,
                maxNumberOfTickets: 0,

                regularTicketPrice: 0,
                fanTicketPrice: 0,
                vipTicketPrice: 0,

                holdingDate: "",
                description: "",
                status: "CREATED",
                type: "CONCERT",

                numberOfRegularTicketsLeft: 0,
                numberOfFanTicketsLeft: 0,
                numberOfVipTicketsLeft: 0,

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
                data: [],
                totalNumberOfResults: 0,
                hasPreviousPage: null,
                hasNextPage: null
            },
            reviewTypeValue: ""
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
            const successCallback = (response) => {
                this.reviews = response.data;
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.reviewService.getReviews(
                this.$route.params.id,
                this.reviewPage,
                this.reviewSizeStr,
                {
                    filterStatus: this.reviewTypeValue
                },
                successCallback,
                errorCallback
            )
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
                const status = error.response.status;
                const msg = error.response.data;
                const expectedError = `Manifestation with id ${this.$route.params.id} not found`;

                if (status == 404) {
                    if (msg == expectedError) {
                        this.$root.failureToast(msg);
                    }
                    this.$root.defaultCatchError(error);       
                } else {
                    this.$root.defaultCatchError(error); 
                }
            };

            this.$refs.manifestationService.getManifestation(manifestationId, successCallback, errorCallback);
        },

        getCanLeaveReview: function() {
            if (this.$root.isCustomer()) {
                const successCallback = (response) => {
                    this.canLeaveReview = response.data;
                };
                const errorCallback = (error) => {
                    this.canLeaveReview = false;
                    this.$root.defaultCatchError(error);
                };

                this.$refs.reviewService.canLeaveReview(
                    this.$route.params.id,
                    successCallback,
                    errorCallback
                );
            } else {
                this.canLeaveReview = false;
            }
        },

        getBelongsToSalesman: function() {
            if (this.$root.isSalesman()) {
                const successCallback = (response) => {
                    this.belognsToSalesman = response.data;
                };
                const errorCallback = (error) => {
                    this.belognsToSalesman = false;
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

        deleteManifestation: function() {
            const successCallback = (response) => {
                this.$root.successToast("Manifestation is deleted")
                this.redirectToManifestation();
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.deleteManifestation(
                this.manifestation.id,
                successCallback,
                errorCallback
            );
        },

        endManifestation: function(manifestationId) {
            const successCallback = (response) => {
                this.$root.successToast("Manifestation is ended");
                this.getManifestation(this.manifestation.id);
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.endManifestation(
                manifestationId,
                successCallback,
                errorCallback
            );
        },

        rejectManifestation: function() {
            const successCallback = (response) => {
                this.$root.successToast("Manifestation is rejected");
                this.getManifestation(this.manifestation.id);
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.approveOrReject(
                this.manifestation.id,
                "REJECTED",
                successCallback,
                errorCallback
            );
        },

        approveManifestation: function() {
            const successCallback = (response) => {
                this.$root.successToast("Manifestation is approved");
                this.getManifestation(this.manifestation.id);
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.approveOrReject(
                this.manifestation.id,
                "ACTIVE",
                successCallback,
                errorCallback
            );
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

            
            const pathTo = `${rolePath}/manifestations/map`;
            this.$router.push({ path: pathTo });
        },

        changeDetails: function() {
            this.$refs.changeManifestationModal.getManifestation(this.manifestation.id);
            $("#changeManifestationModal").modal("show");
        },
        
        changeLocation: function() {
            this.$refs.changeLocationModal.getManifestation(this.manifestation.id);
            $("#changeLocationModal").modal("show");
        },

        showReserveTicketsModal: function() {
            this.$refs.reserveTicketsModal.calculatePricesWithDiscount();
            $("#reserveTicketsModal").modal("show");
        },

        changeReviewType: function(newReviewType) {
            this.reviewTypeValue = newReviewType;
            this.reviewPage = 0;
            this.getReviews();
        },

        addReview: function(reviewToAdd) {
            const successCallback = (response) => {
                this.$root.successToast("Review is created. But salesman must approve it.");
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.reviewService.addReview(
                this.$route.params.id,
                reviewToAdd,
                successCallback,
                errorCallback
            );
        },

        approveReview: function(reviewId) {
            const successCallback = (response) => {
                this.getReviews();
                this.getManifestation(this.manifestation.id);
                this.$root.successToast("Review is approved");
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.reviewService.approveOrReject(
                reviewId,
                "APPROVED",
                successCallback,
                errorCallback
            );
        },

        rejectReview: function(reviewId) {
            const successCallback = (response) => {
                this.getReviews();
                this.$root.successToast("Review is rejected");
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.reviewService.approveOrReject(
                reviewId,
                "REJECTED",
                successCallback,
                errorCallback
            );
        },

        deleteReview: function(reviewId) {
            const successCallback = (response) => {
                this.getReviews();
                this.getManifestation(this.manifestation.id);
                this.$root.successToast("Review is deleted");
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.reviewService.deleteReview(
                reviewId,
                successCallback,
                errorCallback
            );
        }
    },

    computed: {
        formattedAddress() {
            const address = this.address;
            return address.street + " " + address.number + ", " + address.city + ", " + address.postalCode;
        },
        
        parseRating() {
            // const roundNumber = Math.round(this.manifestation.avgRating * 100) / 100;
            // const roundNumber = Number(this.manifestation.avgRating);
            // const roundString = roundNumber.toFixed(2);
            return this.manifestation.avgRating.toFixed(2);
        },

        reviewTypeValueComputed() {
            if (this.$root.isUserLoggedIn()) {
                if (this.$root.isCustomer()) {
                    return "APPROVED";
                } else {
                    return this.reviewTypeValue;
                }
            } else {
                return "APPROVED";
            }
        },

        reviewTypeOptionscomputed() {
            if (this.$root.isUserLoggedIn()) {
                if (this.$root.isCustomer()) {
                    return ["APPROVED"];
                } else {
                    return [
                        "",
                        "CREATED",
                        "APPROVED",
                        "REJECTED"
                    ];
                }
            } else {
                return ["APPROVED"];
            }
        }
    },

    mounted() {
        this.reviewTypeValue = this.reviewTypeValueComputed;

        const id = this.$route.params.id;
        this.getManifestation(id);
        this.getReviews();

        this.getBelongsToSalesman();
        this.getCanLeaveReview();
    },

    destroyed() {}
});
