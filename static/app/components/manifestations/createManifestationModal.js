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
                <div class="form-group col-md-12">
                    <textAreaInput
                        name="description"
                        labelText="Description"
                        class="form-group w-100"
                        height="200px"
                        v-model="manifestationToCreate.description"
                        v-bind:errorMessage="descriptionErrorMessage"
                        v-bind:isInvalid="isDescriptionInvalid"
                        required
                    >
                    </textAreaInput>
                </div>
            </div>
            {{ JSON.stringify(manifestationToCreate, null, 4) }}

            <br/>
            <br/>
            EXAMPLES

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="inputEmail4">Email</label>
                    <input required type="email" class="form-control" id="inputEmail4">
                </div>
                <div class="form-group col-md-6">
                    <label for="inputPassword4">Password</label>
                    <input type="password" class="form-control" id="inputPassword4">
                </div>
            </div>
            <div class="form-group">
                <label for="inputAddress">Address</label>
                <input type="text" class="form-control" id="inputAddress" placeholder="1234 Main St">
            </div>
            <div class="form-group">
                <label for="inputAddress2">Address 2</label>
                <input type="text" class="form-control" id="inputAddress2" placeholder="Apartment, studio, or floor">
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="inputCity">City</label>
                    <input type="text" class="form-control" id="inputCity">
                </div>
                <div class="form-group col-md-4">
                    <label for="inputState">State</label>
                    <select id="inputState" class="form-control">
                        <option selected>Choose...</option>
                        <option>...</option>
                    </select>
                </div>
                <div class="form-group col-md-2">
                    <label for="inputZip">Zip</label>
                    <input type="text" class="form-control" id="inputZip">
                </div>
            </div>
            <div class="form-group">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="gridCheck">
                    <label class="form-check-label" for="gridCheck">
                        Check me out
                    </label>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Sign in</button>
        </baseForm>
    </baseModal>
    `,

    props: {
        id: String
    },

    data: function() {
        return {
            manifestationToCreate: {
                name: "",//
                maxNumberOfTickets: 0,//
                regularTicketPrice: 0,//
                holdingDate: "",//
                description: "",//
                status: "CREATED",//
                type: "CONCERT",//

                latitude: 0,
                longitude: 0,

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

            imageFile: {},
            ImageTypes: Object.freeze({
                PNG:  ".png",
                JPG:  ".jpg",
                JPEG: ".jpeg"
            }),




            nameErrorMessage: "Name must not be empty",
            priceErrorMessage: "Price must be positive number",
            dateErrorMessage: "Holding date must not be empty",
            descriptionErrorMessage: "Description must not be empty",

            
            isNameInvalid: false,
            isPriceInvalid: false,
            isDateInvalid: false,
            isDescriptionInvalid: false
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
            this.descriptionErrorMessage = "Description must not be empty";
            this.isDescriptionInvalid = false;
        },



        removeValidation: function() {
            // var form = $("#createManifestationForm");
            // form.removeClass("was-validated");

            this.$refs.createManifestationForm.removeValidation();

            this.removeInvalidNameError();
            this.removeInvalidPriceError();
            this.removeInvalidDateError();

            // this.removeInvalidSurnameError();
            // this.removeInvalidUserNameError();
            // this.removeInvalidDateError();
            // this.removeInvalidPasswordError();
            // this.removeInvalidRepPasError();
        },





        validateForm: function() {
            // var form = $("#createManifestationForm");
            // // var form = document.getElementById("createManifestationForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();

            var isValid = this.$refs.createManifestationForm.validateForm();

            if (this.manifestationToCreate.regularTicketPrice === "" || this.manifestationToCreate.regularTicketPrice === 0) {
                this.showInvalidPriceError("Password must not be empty");
                isValid = false;
            }

            return isValid;
        },
        
        changeType: function(newType) {
            this.manifestationToCreate.type = newType;
        },
        
        createManifestation: function() {
            this.removeValidation();
            if (this.validateForm()) {
                this.closeModal();
            }
        },

        cancel: function() {
            console.log("Nemanja");
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

                latitude: 0,
                longitude: 0,

                street: "",
                number: 0,
                city: "",
                postalCode: "",

                imageBase64: "",
                imageType: ""
            },
            this.imageFile = {}

            this.removeValidation();
        },
    },

    mounted() {},

    destroyed() {}
});
