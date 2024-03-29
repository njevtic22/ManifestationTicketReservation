Vue.component("addSalesmanModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Add Salesman"
        successBtnText="Add Salesman"
        cancelBtnText="Cancel"

        v-on:successEvent="createSalesman"
        v-on:cancelEvent="cancel"
    >
        <baseForm
            id="addSalesmanForm"
            ref="addSalesmanForm"
        >
            <div class="form-row">
                <div class="form-group col-md-6">
                    <textInput
                        name="name"
                        labelText="Name"
                        class="form-group"
                        v-model="newSalesman.name"
                        v-bind:errorMessage="nameErrorMessage"
                        v-bind:isInvalid="isNameInvalid"
                        required
                    >
                    </textInput>
                </div>

                <div class="form-group col-md-6">
                    <textInput
                        name="surname"
                        labelText="Surname"
                        class="form-group"
                        v-model="newSalesman.surname"
                        v-bind:errorMessage="surnameErrorMessage"
                        v-bind:isInvalid="isSurnameInvalid"
                        required
                    >
                    </textInput>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <textInput
                        name="username"
                        labelText="Username"
                        class="form-group"
                        v-model="newSalesman.username"
                        v-bind:errorMessage="usernameErrorMessage"
                        v-bind:isInvalid="isUsernameInvalid"
                        required
                    >
                    </textInput>
                </div>

                <div class="form-group col-md-6">
                    <classicDateInput
                        name="date"
                        labelText="Date"
                        class="form-group"
                        v-model="newSalesman.dateOfBirth"
                        v-bind:errorMessage="dateErrorMessage"
                        v-bind:isInvalid="isDateInvalid"
                        v-bind:maxDate="new Date()"
                        required
                        ref="dateInput"
                    >
                    </classicDateInput>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <passwordInput
                        name="password"
                        labelText="Password"
                        class="form-group"
                        v-model="newSalesman.password"
                        v-bind:errorMessage="passwordErrorMessage"
                        v-bind:isInvalid="isPasswordInvalid"
                        required
                    >
                    </passwordInput>
                </div>

                <div class="form-group col-md-6">
                    <passwordInput
                        name="passwordRepeat"
                        labelText="Repeat password"
                        class="form-group"
                        v-model="newSalesman.passwordRepeat"
                        v-bind:errorMessage="pasRepErrorMessage"
                        v-bind:isInvalid="isPasRepInvalid"
                        required
                    >
                    </passwordInput>
                </div>
            </div>

            <div class="text-center">
                <fieldset class="btn-group btn-group-toggle" data-toggle="buttons">
                    <legend>Gender</legend>
                    <label class="btn btn-secondary active">
                        <input type="radio" name="genderMale" v-bind:value="Genders.MALE" v-model="newSalesman.gender"/>{{ Genders.MALE }}
                    </label>
                    <label class="btn btn-secondary">
                        <input type="radio" name="genderFemale" v-bind:value="Genders.FEMALE" v-model="newSalesman.gender"/>{{ Genders.FEMALE }}
                    </label>
                </fieldset>
            </div>
        </baseForm>

        
        <authenticationService ref="authService"></authenticationService>
    </baseModal>
    `,

    props: {
        id: String
    },

    data: function() {
        return {
            newSalesman: {
                name: "",
                surname: "",
                username: "",
                password: "",
                passwordRepeat: "",
                dateOfBirth: "",
                gender: ""
            },

            Genders: Object.freeze({
                MALE: "MALE",
                FEMALE: "FEMALE"
            }),


            nameErrorMessage: "Name must not be empty",
            surnameErrorMessage: "Surname must not be empty",
            usernameErrorMessage: "Username is invalid or taken",
            passwordErrorMessage: "Password must not be empty",
            pasRepErrorMessage: "Password must be repeated",
            dateErrorMessage: "Date must be in format " + this.$root.getDateFormat(),

            isNameInvalid: false,
            isSurnameInvalid: false,
            isUsernameInvalid: false,
            isPasswordInvalid: false,
            isPasRepInvalid: false,
            isDateInvalid: false

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

        showInvalidSurnameError: function(message) {
            this.surnameErrorMessage = message;
            this.isSurnameInvalid = true;
        },

        removeInvalidSurnameError: function() {
            this.surnameErrorMessage = "Surname must not be empty";
            this.isSurnameInvalid = false;
        },

        showInvalidUserNameError: function(message) {
            this.userNameErrorMessage = message;
            console.log(this.userNameErrorMessage);
            this.isUsernameInvalid = true;
        },

        removeInvalidUserNameError: function() {
            this.userNameErrorMessage = "Username is invalid or taken";
            this.isUsernameInvalid = false;
        },

        showInvalidDateError: function(message) {
            this.dateErrorMessage = message;
            this.isDateInvalid = true;
        },

        removeInvalidDateError: function() {
            this.dateErrorMessage = "Date must be in format " + this.$root.getDateFormat();
            this.isDateInvalid = false;
        },

        showInvalidPasswordError: function(message) {
            this.passwordErrorMessage = message;
            this.isPasswordInvalid = true;
        },

        removeInvalidPasswordError: function() {
            this.passwordErrorMessage = "Password must not be empty";
            this.isPasswordInvalid = false;
        },

        showInvalidRepPasError: function(message) {
            this.pasRepErrorMessage = message;
            this.isPasRepInvalid = true;
        },

        removeInvalidRepPasError: function() {
            this.pasRepErrorMessage = "Password must not be repeated";
            this.isPasRepInvalid = false;
        },

        validateForm: function() {
            // var form = $("#addSalesmanForm");
            // // var form = document.getElementById("addSalesmanForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();

            var isValid = this.$refs.addSalesmanForm.validateForm();

            if (this.newSalesman.password === "") {
                this.showInvalidPasswordError("Password must not be empty");
                isValid = false;
            }
            if (this.newSalesman.passwordRepeat === "") {
                this.showInvalidRepPasError("Password must not be empty");
                isValid = false;
            }
        
            if (this.newSalesman.password !== this.newSalesman.passwordRepeat) {
                this.showInvalidRepPasError("Passowrds do not match");
                this.showInvalidPasswordError("Passowrds do not match");
                isValid = false;
            }

            return isValid;
        },

        removeValidation: function() {
            // var form = $("#addSalesmanForm");
            // form.removeClass("was-validated");
            this.$refs.addSalesmanForm.removeValidation();

            this.removeInvalidNameError();
            this.removeInvalidSurnameError();
            this.removeInvalidUserNameError();
            this.removeInvalidDateError();
            this.removeInvalidPasswordError();
            this.removeInvalidRepPasError();
        },

        createSalesman: function(event) {
            if (this.validateForm()) {
                this.removeValidation();
                this.newSalesman.dateOfBirth += " 08:00:00";

                const successCallback = (response) => {
                    this.closeModal();
                    this.$emit('salesmanCreatedEvent');
                    this.$root.successToast("New salesman is created.", 3600000);
                };

                const errorCallback = (error) => {
                    if (error.response.data === "Username " + this.newSalesman.username + " is taken.") {
                        this.showInvalidUserNameError(error.response.data);
                    } else if (error.response.data.startsWith("Unparseable date:")) {
                        this.showInvalidDateError("Date must be in format " + this.$root.getDateFormat());
                    } else {
                        this.$root.defaultCatchError(error);
                    }
                };

                this.$refs.authService.registerSalesman(this.newSalesman, successCallback, errorCallback);
            }
        },

        cancel: function(event) {
            this.closeModal();
            // this.$emit('cancelEvent', event)
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {
            this.newSalesman.name = "";
            this.newSalesman.surname = "";
            this.newSalesman.username = "";
            this.newSalesman.password = "";
            this.newSalesman.passwordRepeat = "";
            this.newSalesman.dateOfBirth = "";
            this.newSalesman.gender = this.Genders.MALE;

            this.removeValidation();
        }
    },

    mounted() {
        this.newSalesman.gender = this.Genders.MALE;
    },

    destroyed() {}
});
