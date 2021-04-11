Vue.component("createManifestationModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Create Manifestation"
        modalClass="modal-lg"
        successBtnText="Create Manifestation"
        cancelBtnText="Cancel"

        v-on:successEvent="createManifestation"
        v-on:cancelEvent="cancel"
    >
        <baseForm
            id="createManifestationForm" 
            ref="createManifestationForm"
        >
            <div class="form-row">
                <div class="form-group col-md-12">
                    <textInput
                        name="name"
                        labelText="Name"
                        class="form-group"
                        v-model="manifestationToCreate.name"
                        v-bind:errorMessage="nameErrorMessage"
                        v-bind:isInvalid="isNameInvalid"
                        required
                    >
                    </textInput>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-4">
                    <selectInput
                        name="manType"
                        labelText="Type"
                        v-bind:value="manifestationToCreate.type"
                        v-bind:options="typeOptions"
                        class="form-group"
                        required
                        
                        v-on:select="changeType($event)"
                    ></selectInput>
                </div>
                <div class="form-group col-md-4">
                    <numberInput
                        class="form-group"
                        name="regularTicketPrice"
                        labelText="Regular ticket price"
                        v-model="manifestationToCreate.regularTicketPrice"
                        v-bind:errorMessage="priceErrorMessage"
                        v-bind:isInvalid="isPriceInvalid"
                        required
                    >
                    </numberInput>
                </div>

                <div class="form-group col-md-4">
                    <classicDateTimeInput
                        name="date"
                        class="form-group"
                        labelText="Date and time"
                        v-model="manifestationToCreate.holdingDate"
                        v-bind:minDate="new Date()"
                        v-bind:errorMessage="dateErrorMessage"
                        v-bind:isInvalid="isDateInvalid"
                        required
                    >
                    </classicDateTimeInput>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <img 
                        class="w-100"
                        alt="Image not found"
                        v-bind:src="imageToShow"
                        v-on:error="showAlternateImage"
                    >
                    <br/>
                    <br/>
                    <div class="custom-file">
                        <input 
                            type="file" 
                            class="custom-file-input" 
                            id="fileInput" 
                            v-on:change="changeFile($event)" 
                            v-bind:accept="imagesToAccept"
                        >
                        <label class="custom-file-label" for="customFile">Choose image</label>
                    </div>
                </div>
                <div class="form-group col-md-6">
                    <textAreaInput
                        name="description"
                        placeholder="Description"
                        class="form-group w-100 h-100"
                        height="440px"
                        v-model="manifestationToCreate.description"
                        v-bind:errorMessage="descriptionErrorMessage"
                        v-bind:isInvalid="isDescriptionInvalid"
                        required
                    >
                    </textAreaInput>
                </div>
            </div>

            <br/>
            <hr/>
            <h4>Location</h4>


            <div class="form-row">
                <div class="form-group col-md-6">
                    <numberInput
                        name="latitude"
                        labelText="Latitude"
                        v-model="manifestationToCreate.latitude"
                        required
                        disabled
                    >
                    </numberInput>
                </div>
                <div class="form-group col-md-6">
                    <numberInput
                        name="longitude"
                        labelText="Longitude"
                        v-model="manifestationToCreate.longitude"
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
                        v-model="manifestationToCreate.street"
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
                        v-model="manifestationToCreate.number"
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
                        v-model="manifestationToCreate.city"
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
                        v-model="manifestationToCreate.postalCode"
                        v-bind:errorMessage="postalCodeErrorMessage"
                        v-bind:isInvalid="isPostalCodeInvalid"
                        required
                    >
                    </textInput>
                </div>
            </div>

            <button type="button" class="btn btn-primary btn-block" v-on:click="geocodeCoordinates">View on map</button>

            <add-map
                style="height: 500px; width: 100%;"
                v-bind:newManifestation="manifestationToCreate"
                v-on:coordsChosen="changeLocation($event)"  
            >
            </add-map>



            <geocodeService ref="geocodeService"></geocodeService>
            <manifestationService ref="manifestationService"></manifestationService>
        </baseForm>
    </baseModal>
    `,

    props: {
        id: String
    },

    data: function() {
        return {
            manifestationToCreate: {
                name: "",
                maxNumberOfTickets: 0,
                regularTicketPrice: 0,
                holdingDate: "",
                description: "",
                status: "CREATED",
                type: "CONCERT",

                latitude: 45.25603382101638,
                longitude: 19.845723284250592,

                street: "",
                number: 0,
                city: "",
                postalCode: "",

                imageBase64: "",
                imageType: ""
            },

            typeOptions: Object.freeze([
                "CONCERT",
                "FESTIVAL",
                "THEATER"
            ]),

            imageTypes: Object.freeze([
                "png",
                "jpg",
                "jpeg",

                "tif",
                "tiff",
                "bmp"
            ]),
            imagesToAccept: "",
            imageToShow: "/images/no image 2.png",



            nameErrorMessage: "Name must not be empty",
            priceErrorMessage: "Price must be positive number",
            dateErrorMessage: "Holding date must not be empty",
            descriptionErrorMessage: "Description must have at least 100 characters",

            streetErrorMessage: "Street must not be empty",
            numberErrorMessage: "Number must not be empty",
            cityErrorMessage: "City must not be empty",
            postalCodeErrorMessage: "Zip code must not be empty",

            
            isNameInvalid: false,
            isPriceInvalid: false,
            isDateInvalid: false,
            isDescriptionInvalid: false,

            isStreetInvalid: false,
            isNumberInvalid: false,
            isCityInvalid: false,
            isPostalCodeInvalid: false
        };
    },

    // computed: {
    //     imageToShow: {
    //         if (this.manifestationToCreate.imageBase64 === "") {
    //             return "/images/no image 2.png";
    //         }
    //         return this.manifestationToCreate.imageBase64;
    //     }
    // },

    methods: {
        showInvalidNameError: function(message) {
            this.nameErrorMessage = message;
            this.isNameInvalid = true;
        },

        removeInvalidNameError: function() {
            this.nameErrorMessage = "Name must not be empty";
            this.isNameInvalid = false;
        },

        showInvalidPriceError: function(message) {
            this.priceErrorMessage = message;
            this.manifestationToCreate.regularTicketPrice = "";
            this.isPriceInvalid = true;
        },

        removeInvalidPriceError: function() {
            this.priceErrorMessage = "Price must be positive number",
            this.isPriceInvalid = false;
        },

        showInvalidDateError: function(message) {
            this.dateErrorMessage = message;
            this.isDateInvalid = true;
        },

        removeInvalidDateError: function() {
            this.dateErrorMessage = "Holding date must not be empty";
            this.isDateInvalid = false;
        },

        showInvalidDescriptionError: function(message) {
            this.descriptionErrorMessage = message;
            this.isDescriptionInvalid = true;
        },

        removeInvalidDescriptionError: function() {
            this.descriptionErrorMessage = "Description must have at least 100 characters";
            this.isDescriptionInvalid = false;
        },

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
            // var form = $("#createManifestationForm");
            // form.removeClass("was-validated");

            this.$refs.createManifestationForm.removeValidation();

            this.removeInvalidNameError();
            this.removeInvalidPriceError();
            this.removeInvalidDateError();
            this.removeInvalidDescriptionError();
            this.removeInvalidStreetError();
            this.removeInvalidNumberError();
            this.removeInvalidCityError();
            this.removeInvalidPostalCodeError();
        },

        validateForm: function() {
            // var form = $("#createManifestationForm");
            // // var form = document.getElementById("createManifestationForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();

            var isValid = this.$refs.createManifestationForm.validateForm();

            if (this.manifestationToCreate.regularTicketPrice === "" || this.manifestationToCreate.regularTicketPrice === 0) {
                this.showInvalidPriceError("Price must be positive number");
                isValid = false;
            }

            if (this.manifestationToCreate.number === "" || this.manifestationToCreate.number === 0) {
                this.showInvalidNumberError("Number must not be empty");
                isValid = false;
            }

            return isValid;
        },
        
        changeFile: function(event) {
            // fname.slice((fname.lastIndexOf(".") - 1 >>> 0) + 2);
            // fname.slice((Math.max(0, fname.lastIndexOf(".")) || Infinity) + 1);

            // var fileName = event.target.value.slice(event.target.value.lastIndexOf("\\") + 1);
            // var fileExtension = fileName.slice((fileName.lastIndexOf(".") - 1 >>> 0) + 2);

            
            this.manifestationToCreate.imageType = "";
            this.manifestationToCreate.imageBase64 = "";
            this.imageToShow = "/images/no image 2.png";

            
            var fileData = $('#fileInput').prop('files')[0];
            if (fileData == null) {
                return;
            }


            var fileExtension = fileData.name.slice((fileData.name.lastIndexOf(".") - 1 >>> 0) + 2);
            if (fileExtension === "") {
                return;
            }

            var isTypeValid = false;
            for (let i = 0; i < this.imageTypes.length; i++) {
                const type = this.imageTypes[i];
                if (fileExtension === type) {
                    isTypeValid = true;
                    break;
                }
            }

            if (!isTypeValid) {
                return;
            }

            // this gets jpg, while FileReader gets jpeg
            // this.manifestationToCreate.imageType = fileExtension;

            var self = this;
            var reader = new FileReader();
            reader.onload = function(e) {
                self.manifestationToCreate.imageBase64 = e.target.result;
                self.imageToShow = e.target.result;

                // data:image/jpeg;base64,
                var base64Header = self.imageToShow.slice(0, self.imageToShow.indexOf(",") + 1);
                
                self.manifestationToCreate.imageType = base64Header.slice(base64Header.indexOf("/") + 1, base64Header.indexOf(";"));
            };
            reader.readAsDataURL(fileData);
        },

        showAlternateImage: function() {
            this.imageToShow = "/images/no image 2.png";
        },

        changeType: function(newType) {
            this.manifestationToCreate.type = newType;
        },

        geocodeCoordinates: function() {
            const successCallback = (coordinates) => {
                this.manifestationToCreate.latitude = coordinates.latitude;
                this.manifestationToCreate.longitude = coordinates.longitude;
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.geocodeService.geocode(
                this.manifestationToCreate.street,
                this.manifestationToCreate.number,
                this.manifestationToCreate.city,
                successCallback,
                errorCallback
            );
        },

        geocodeAddress: function(coordinates) {
            const successCallback = (address) => {
                this.manifestationToCreate.street = address.street;
                this.manifestationToCreate.number = address.number;
                this.manifestationToCreate.city   = address.city;
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
            this.manifestationToCreate.latitude  = coordinates.latitude;
            this.manifestationToCreate.longitude = coordinates.longitude;

            this.geocodeAddress(coordinates);
        },

        createManifestation: function() {
            this.removeValidation();
            if (this.validateForm()) {
                const successCallback = (response) => {
                    this.closeModal();
                    this.$root.successToast("Manifestation is created");
                    this.$emit("manifestationCreated");
                };
                const errorCallback = (error) => {
                    this.$root.defaultCatchError(error);
                };

                this.$refs.manifestationService.addManifestation(
                    this.manifestationToCreate,
                    successCallback,
                    errorCallback
                );
            }
        },

        cancel: function() {
            this.closeModal();
            // this.$emit('cancelEvent', event)
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {
            this.manifestationToCreate = {
                name: "",
                maxNumberOfTickets: 0,
                regularTicketPrice: 0,
                holdingDate: "",
                description: "",
                status: "CREATED",
                type: "CONCERT",

                latitude: 45.25603382101638,
                longitude: 19.845723284250592,

                street: "",
                number: 0,
                city: "",
                postalCode: "",

                imageBase64: "",
                imageType: ""
            },
            this.imageToShow = "/images/no image 2.png",

            this.removeValidation();
        },
    },

    mounted() {
        this.imageTypes.forEach(imageType => {
            this.imagesToAccept += `image/${imageType},`;
        });
        this.imagesToAccept = this.imagesToAccept.slice(0, -1);
    },

    destroyed() {}
});
