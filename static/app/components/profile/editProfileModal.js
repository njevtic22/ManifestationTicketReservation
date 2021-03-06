Vue.component("editProfileModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        modalTitle="Change profile"
        successBtnText="Apply"
        btnSuccessClass="btn-success"
        cancelBtnText="Cancel"

        v-on:successEvent="updateUser"
        v-on:cancelEvent="cancel"
    >
        <baseForm
            id="changeUserModal"
            ref="changeUserForm"
        >
            <div class="form-row">
                <div class="form-group col-md-6">
                    <textInput
                        name="name"
                        labelText="Name"
                        class="form-group"
                        v-model="user.name"
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
                        v-model="user.surname"
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
                        v-model="user.username"
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
                        v-model="user.dateOfBirth"
                        v-bind:errorMessage="dateErrorMessage"
                        v-bind:isInvalid="isDateInvalid"
                        v-bind:maxDate="new Date()"
                        required
                        ref="dateInput"
                    >
                    </classicDateInput>
                </div>
            </div>

            <div class="text-center">
                <fieldset class="btn-group btn-group-toggle" data-toggle="buttons">
                    <legend>Gender</legend>
                    <label class="btn btn-secondary active">
                        <input type="radio" name="genderMale" v-bind:value="Genders.MALE" v-model="user.gender"/>{{ Genders.MALE }}
                    </label>
                    <label class="btn btn-secondary">
                        <input type="radio" name="genderFemale" v-bind:value="Genders.FEMALE" v-model="user.gender"/>{{ Genders.FEMALE }}
                    </label>
                </fieldset>
            </div>
        </baseForm>

        <adminService ref="adminService"></adminService>
        <salesmanService ref="salesmanService"></salesmanService>
        <customerService ref="customerService"></customerService>
        <authenticationService ref="authenticationService"></authenticationService>
    </baseModal>
    `,

    props: {
        id: String,
    },

    data: function() {
        return {
            user: {
                id: 0,
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

        validateForm: function() {
            // var form = $("#changeUserForm");
            // // var form = document.getElementById("changeUserForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();

            return this.$refs.changeUserForm.validateForm();

        },

        removeValidation: function() {
            // var form = $("#changeUserForm");
            // form.removeClass("was-validated");
            this.$refs.changeUserForm.removeValidation();

            this.removeInvalidNameError();
            this.removeInvalidSurnameError();
            this.removeInvalidUserNameError();
            this.removeInvalidDateError();
        },

        updateUser: function() {
            if (this.validateForm()) {
                this.removeValidation();
                this.user.dateOfBirth += " 08:00:00";

                const successCallback = (response) => {
                    this.closeModal();
                    this.$emit('profileChanged');
                    this.$root.successToast("Profile changed.");
                };

                const errorCallback = (error) => {
                    if (error.response.data === "Username " + this.user.username + " is taken.") {
                        this.showInvalidUserNameError(error.response.data);
                    } else if (error.response.data.startsWith("Unparseable date:")) {
                        this.showInvalidDateError("Date must be in format " + this.$root.getDateFormat());
                    } else {
                        this.$root.defaultCatchError(error);
                    }
                };

                const userData = {
                    name: this.user.name,
                    surname: this.user.surname,
                    username: this.user.username,
                    dateOfBirth: this.user.dateOfBirth,
                    gender: this.user.gender
                };

                if (this.$root.isAdmin()) {
                    this.$refs.adminService.updateAdmin(this.user.id, userData, successCallback, errorCallback);

                } else if (this.$root.isSalesman()) {
                    this.$refs.salesmanService.updateSalesman(this.user.id, userData, successCallback, errorCallback);

                } else if (this.$root.isCustomer()) {
                    this.$refs.customerService.updateCustomer(this.user.id, userData, successCallback, errorCallback);

                } else {
                    this.$root.failureToast("unexpected error occured.", 360000);
                }
            }
        },

        clearModal: function() {
            this.user.name = "";
            this.user.surname = "";
            this.user.username = "";
            this.user.password = "";
            this.user.passwordRepeat = "";
            this.user.dateOfBirth = "";
            this.user.gender = this.Genders.MALE;
            
            this.removeValidation();
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        cancel: function() {
            this.closeModal();
            // this.$emit('cancelEvent', event)
        },
        
        getUser: function() {
            const successCallback = (response) => {
                this.user = response.data;
                this.user.dateOfBirth = this.user.dateOfBirth.substring(0, 11);
            };

            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.authenticationService.getAuthenticated(successCallback, errorCallback);  
        },
    },

    mounted() {
        this.user.gender = this.Genders.MALE;
        // this.getUser();
    },

    destroyed() {}
});
