Vue.component("changeLocationModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Change Location"
        modalClass="modal-lg"
        successBtnText="Apply"
        cancelBtnText="Cancel"

        v-on:successEvent="applyChanges"
        v-on:cancelEvent="cancel"
    >
        <baseForm
            id="changeLocationForm"
            ref="changeLocationForm"
        >
            <div class="form-row">
                <div class="form-group col-md-6">
                    <numberInput
                        name="latitude"
                        labelText="Latitude"
                        v-model="location.latitude"
                        required
                        disabled
                    >
                    </numberInput>
                </div>
                <div class="form-group col-md-6">
                    <numberInput
                        name="longitude"
                        labelText="Longitude"
                        v-model="location.longitude"
                        required
                        disabled
                    >
                    </numberInput>
                </div>
            </div>
            

            <div class="form-row">
                <div class="form-group col-md-3">
                    <textInput
                        name="street"
                        labelText="Street"
                        class="form-group"
                        v-model="address.street"
                        v-bind:errorMessage="streetErrorMessage"
                        v-bind:isInvalid="isStreetInvalid"
                        required
                    >
                    </textInput>
                </div>
                <div class="form-group col-md-3">
                    <numberInput
                        class="form-group"
                        name="number"
                        labelText="Number"
                        v-model="address.number"
                        v-bind:errorMessage="numberErrorMessage"
                        v-bind:isInvalid="isNumberInvalid"
                        required
                    >
                    </numberInput>
                </div>

                <div class="form-group col-md-3">
                    <textInput
                        name="city"
                        labelText="City"
                        class="form-group"
                        v-model="address.city"
                        v-bind:errorMessage="cityErrorMessage"
                        v-bind:isInvalid="isCityInvalid"
                        required
                    >
                    </textInput>
                </div>

                <div class="form-group col-md-3">
                    <textInput
                        name="zipCode"
                        labelText="Zip code"
                        class="form-group"
                        v-model="address.postalCode"
                        v-bind:errorMessage="postalCodeErrorMessage"
                        v-bind:isInvalid="isPostalCodeInvalid"
                        required
                    >
                    </textInput>
                </div>
            </div>

            <button type="button" class="btn btn-primary btn-block" v-on:click="geocodeCoordinates">View on map</button>

            <div>
                <div class="d-flex justify-content-center" v-if="location.latitude == null || location.longitude == null">
                    <div class="spinner-grow text-secondary" role="status" style="width: 3rem; height: 3rem;">
                        <span class="sr-only">Loading...</span>
                    </div>
                </div>
                <div v-else>
                    <change-map
                        style="height: 500px; width: 100%;"
                        v-bind:zoom="15"
                        v-bind:location="[location.latitude, location.longitude]"
                        v-on:coordsChosen="changeLocation($event)"  
                    >
                    </change-map>
                </div>
            </div>

        </baseForm>

        <geocodeService ref="geocodeService"></geocodeService>
        <manifestationService ref="manifestationService"></manifestationService>
    </baseModal>
    `,

    props: {
        id: String,
    },

    data: function() {
        return {
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
            },

            location: {
                id: 0,
                latitude: null,
                longitude: null
            },
            address: {
                street: "", 
                number: 0, 
                city: "", 
                postalCode: ""
            },

            spareLocation: {
                id: 0,
                latitude: 0,
                longitude: 0
            },
            spareAddress: {
                street: "", 
                number: 0, 
                city: "", 
                postalCode: ""
            },

            
            streetErrorMessage: "Street must not be empty",
            numberErrorMessage: "Number must not be empty",
            cityErrorMessage: "City must not be empty",
            postalCodeErrorMessage: "Zip code must not be empty",
            
            isStreetInvalid: false,
            isNumberInvalid: false,
            isCityInvalid: false,
            isPostalCodeInvalid: false
        };
    },

    methods: {
        showInvalidStreetError: function(message) {
            this.streetErrorMessage = message;
            this.isStreetInvalid = true;
        },

        removeInvalidStreetError: function() {
            this.streetErrorMessage = "Street must not be empty";
            this.isStreetInvalid = false;
        },

        showInvalidNumberError: function(message) {
            this.numberErrorMessage = message;
            this.isNumberInvalid = true;
        },

        removeInvalidNumberError: function() {
            this.numberErrorMessage = "Number must not be empty";
            this.isNumberInvalid = false;
        },

        showInvalidCityError: function(message) {
            this.cityErrorMessage = message;
            this.isCityInvalid = true;
        },

        removeInvalidCityError: function() {
            this.cityErrorMessage = "City must not be empty";
            this.isCityInvalid = false;
        },

        showInvalidPostalCodeError: function(message) {
            this.postalCodeErrorMessage = message;
            this.isPostalCodeInvalid = true;
        },

        removeInvalidPostalCodeError: function() {
            this.postalCodeErrorMessage = "Zip code must not be empty";
            this.isPostalCodeInvalid = false;
        },



        removeValidation: function() {
            // var form = $("#changeLocationForm");
            // form.removeClass("was-validated");

            this.$refs.changeLocationForm.removeValidation();

            this.removeInvalidStreetError();
            this.removeInvalidNumberError();
            this.removeInvalidCityError();
            this.removeInvalidPostalCodeError();
        },

        validateForm: function() {
            // var form = $("#changeLocationForm");
            // // var form = document.getElementById("changeLocationForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();

            var isValid = this.$refs.changeLocationForm.validateForm();

            if (this.address.number === "" || this.address.number === 0) {
                this.showInvalidNumberError("Number must not be empty");
                isValid = false;
            }

            return isValid;
        },

        geocodeCoordinates: function() {
            const successCallback = (coordinates) => {
                this.location.latitude = coordinates.latitude;
                this.location.longitude = coordinates.longitude;
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.geocodeService.geocode(
                this.address.street,
                this.address.number,
                this.address.city,
                successCallback,
                errorCallback
            );
        },

        geocodeAddress: function(coordinates) {
            const successCallback = (address) => {
                this.address.street = address.street;
                this.address.number = address.number;
                this.address.city   = address.city;
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.geocodeService.inverseGeocode(
                coordinates.latitude,
                coordinates.longitude,
                successCallback,
                errorCallback
            );
        },

        changeLocation: function(coordinates) {
            this.location.latitude  = coordinates.latitude;
            this.location.longitude = coordinates.longitude;

            this.geocodeAddress(coordinates);
        },




        applyChanges: function() {
            if (this.validateForm()) {
                this.removeValidation();

                const successCallback = (response) => {
                    this.closeModal();
                    this.$root.successToast("Manifestation is updated");
                    this.$emit("locationUpdated");
                };
                const errorCallback = (error) => {
                    this.$root.defaultCatchError(error);
                };

                const locationData = {
                    id: this.manifestation.id,
                    latitude: this.location.latitude,
                    longitude: this.location.longitude,
                    street: this.address.street,
                    number: this.address.number,
                    city: this.address.city,
                    postalCode: this.address.postalCode
                };

                this.$refs.manifestationService.updateLocation(
                    locationData,
                    successCallback,
                    errorCallback
                );
            }
        },

        cancel: function() {
            this.closeModal();
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {
            this.location = JSON.parse(JSON.stringify(this.spareLocation));
            this.address = JSON.parse(JSON.stringify(this.spareAddress));
        },
        
        getManifestation: function(id) {
            const successCallback = (response) => {
                this.manifestation = response.data;
                this.location = response.data.location;
                this.address = response.data.location.address;

                this.spareLocation = JSON.parse(JSON.stringify(this.location));
                this.spareAddress = JSON.parse(JSON.stringify(this.address));
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.getManifestation(id, successCallback, errorCallback);
        },
    },

    mounted() {},

    destroyed() {}
});
