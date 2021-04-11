Vue.component("changeManifestationModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Change Manifestation"
        modalClass="modal-lg"
        successBtnText="Apply"
        cancelBtnText="Cancel"

        v-on:successEvent="changeManifestation"
        v-on:cancelEvent="cancel"
    >
        <baseForm
            id="changeManifestationForm" 
            ref="changeManifestationForm"
        >
            <div class="form-row">
                <div class="form-group col-md-12">
                    <textInput
                        name="name"
                        labelText="Name"
                        class="form-group"
                        v-model="manifestation.name"
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
                        v-bind:value="manifestation.type"
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
                        v-model="manifestation.regularTicketPrice"
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
                        v-model="manifestation.holdingDate"
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
                        v-model="manifestation.description"
                        v-bind:errorMessage="descriptionErrorMessage"
                        v-bind:isInvalid="isDescriptionInvalid"
                        required
                    >
                    </textAreaInput>
                </div>
            </div>
        </baseForm>
        
        <manifestationService ref="manifestationService"></manifestationService>
    </baseModal>
    `,

    props: {
        id: String
    },

    data: function() {
        return {
            spareManifestation: {},
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

                imageBase64: "",
                imageType: "", 
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
            spareImageToShow: "/images/no image 2.png",

            
            nameErrorMessage: "Name must not be empty",
            priceErrorMessage: "Price must be positive number",
            dateErrorMessage: "Holding date must not be empty",
            descriptionErrorMessage: "Description must have at least 100 characters",
            
            isNameInvalid: false,
            isPriceInvalid: false,
            isDateInvalid: false,
            isDescriptionInvalid: false,
        };
    },

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
            this.manifestation.regularTicketPrice = "";
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

        removeValidation: function() {
            // var form = $("#changeManifestationForm");
            // form.removeClass("was-validated");

            this.$refs.changeManifestationForm.removeValidation();

            this.removeInvalidNameError();
            this.removeInvalidPriceError();
            this.removeInvalidDateError();
            this.removeInvalidDescriptionError();
        },

        validateForm: function() {
            // var form = $("#changeManifestationForm");
            // // var form = document.getElementById("changeManifestationForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();

            var isValid = this.$refs.changeManifestationForm.validateForm();

            if (this.manifestation.regularTicketPrice === "" || this.manifestation.regularTicketPrice === 0) {
                this.showInvalidPriceError("Price must be positive number");
                isValid = false;
            }

            return isValid;
        },
        
        changeFile: function(event) {
            // fname.slice((fname.lastIndexOf(".") - 1 >>> 0) + 2);
            // fname.slice((Math.max(0, fname.lastIndexOf(".")) || Infinity) + 1);

            // var fileName = event.target.value.slice(event.target.value.lastIndexOf("\\") + 1);
            // var fileExtension = fileName.slice((fileName.lastIndexOf(".") - 1 >>> 0) + 2);

            // console.log(fileName);
            // console.log(fileExtension);

            
            this.manifestation.imageType = "";
            this.manifestation.imageBase64 = "";
            this.imageToShow = "/images/no image 2.png";

            
            var fileData = $('#fileInput').prop('files')[0];
            if (fileData == null) {
                return;
            }


            var fileExtension = fileData.name.slice((fileData.name.lastIndexOf(".") - 1 >>> 0) + 2);
            // console.log(fileData.name);
            // console.log(fileExtension);

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
            // this.manifestation.imageType = fileExtension;

            var self = this;
            var reader = new FileReader();
            reader.onload = function(e) {
                self.manifestation.imageBase64 = e.target.result;
                self.imageToShow = e.target.result;

                // data:image/jpeg;base64,
                var base64Header = self.imageToShow.slice(0, self.imageToShow.indexOf(",") + 1);
                
                self.manifestation.imageType = base64Header.slice(base64Header.indexOf("/") + 1, base64Header.indexOf(";"));
            };
            reader.readAsDataURL(fileData);
        },

        showAlternateImage: function() {
            // console.log("showAlternateImage");
            this.imageToShow = "/images/no image 2.png";
        },

        changeType: function(newType) {
            this.manifestation.type = newType;
        },

        changeManifestation: function() {
            if (this.validateForm()) {
                this.removeValidation();
                const successCallback = (response) => {
                    this.closeModal();
                    this.$root.successToast("Manifestation is updated");
                    this.$emit("manifestationUpdated");
                };
                const errorCallback = (error) => {
                    this.$root.defaultCatchError(error);
                };

                const manifestationData = {
                    id: this.manifestation.id,
                    name: this.manifestation.name,
                    regularTicketPrice: this.manifestation.regularTicketPrice,
                    holdingDate: this.manifestation.holdingDate,
                    description: this.manifestation.description,
                    type: this.manifestation.type,
                    imageBase64: this.manifestation.imageBase64,
                    imageType: this.manifestation.imageType
                };

                this.$refs.manifestationService.updateManifestation(
                    manifestationData,
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
            this.manifestation = JSON.parse(JSON.stringify(this.spareManifestation));
            this.imageToShow = JSON.parse(JSON.stringify(this.spareImageToShow));
        },
        
        getManifestation: function(manifestationId) {
            const successCallback = (response) => {
                this.manifestation = response.data;
                this.imageToShow = `data:image/${this.manifestation.imageType};base64,${this.manifestation.imageBase64}`;
                this.manifestation.imageBase64 = this.imageToShow;

                this.spareManifestation = JSON.parse(JSON.stringify(this.manifestation));
                this.spareImageToShow = JSON.parse(JSON.stringify(this.imageToShow));
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.getManifestation(manifestationId, successCallback, errorCallback);
        }
    },

    mounted() {
    },

    destroyed() {}
});
