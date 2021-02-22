Vue.component("registerForm", {
    template: `
    <baseForm
        id="registerForm" 
        class="register-center"
        ref="registerForm"
    >
        <div class="card border-dark">
            <div class="card-header bg-primary text-white">
                <h3>Register</h3>
            </div>
            <div class="card-body">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <textInput
                            name="name"
                            v-model="newCustomer.name"
                            v-bind:errorMessage="nameErrorMessage"
                            v-bind:isInvalid="isNameInvalid"
                            showLabel
                            required
                        >
                            Name
                        </textInput>
                    </div>

                    <div class="form-group col-md-6">
                        <textInput
                            name="surname"
                            v-model="newCustomer.surname"
                            v-bind:errorMessage="surnameErrorMessage"
                            v-bind:isInvalid="isSurnameInvalid"
                            showLabel
                            required
                        >
                            Surname
                        </textInput>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <textInput
                            name="username"
                            v-model="newCustomer.username"
                            v-bind:errorMessage="usernameErrorMessage"
                            v-bind:isInvalid="isUsernameInvalid"
                            showLabel
                            required
                        >
                            Username
                        </textInput>
                    </div>

                    <div class="form-group col-md-6">
                        <classicDateInput
                            name="date"
                            v-model="newCustomer.dateOfBirth"
                            v-bind:errorMessage="dateErrorMessage"
                            v-bind:isInvalid="isDateInvalid"
                            v-bind:maxDate="new Date()"
                            showLabel
                            required
                        >
                            Date
                        </classicDateInput>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <textInput
                            name="password"
                            v-model="newCustomer.password"
                            v-bind:errorMessage="passwordErrorMessage"
                            v-bind:isInvalid="isPasswordInvalid"
                            showLabel
                            required
                        >
                            Password
                        </textInput>
                    </div>

                    <div class="form-group col-md-6">
                        <textInput
                            name="passwordRepeat"
                            v-model="newCustomer.passwordRepeat"
                            v-bind:errorMessage="pasRepErrorMessage"
                            v-bind:isInvalid="isPasRepInvalid"
                            showLabel
                            required
                        >
                            Repeat password
                        </textInput>
                    </div>
                </div>

                <div class="form-group text-center">
                    <fieldset class="btn-group btn-group-toggle" data-toggle="buttons">
                        <legend>Gender</legend>
                        <label class="btn btn-secondary active">
                            <input type="radio" name="genderMale" v-bind:value="Genders.MALE" v-model="newCustomer.gender"/>{{ Genders.MALE }}
                        </label>
                        <label class="btn btn-secondary">
                            <input type="radio" name="genderFemale" v-bind:value="Genders.FEMALE" v-model="newCustomer.gender"/>{{ Genders.FEMALE }}
                        </label>
                    </fieldset>
                </div>
            </div>

            <div class="card-footer text-muted text-center">
                <button type="button" class="btn btn-success" v-on:click="registerCustomer">Register</button>
            </div>
        </div>
    
    </baseForm>
    `,
    data: function() {
        return {
            newCustomer: {
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
            usernameErrorMessage: "Username must not be empty",
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

    // watch: {
    //     // whenever question changes, this function will run
    //     usernameErrorMessage: function (newMessage, oldMessage) {
    //       console.log(newMessage);
    //       console.log(oldMessage);
    //     }
    //   },

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
            this.userNameErrorMessage = "Username must not be empty";
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
            // var form = $("#registerForm");
            // // var form = document.getElementById("registerForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();

            var isValid = this.$refs.registerForm.validateForm();

            if (this.newCustomer.password === "") {
                this.showInvalidPasswordError("Password must not be empty");
                isValid = false;
            }
            if (this.newCustomer.passwordRepeat === "") {
                this.showInvalidRepPasError("Password must not be empty");
                isValid = false;
            }
        
            if (this.newCustomer.password !== this.newCustomer.passwordRepeat) {
                this.showInvalidRepPasError("Passowrds do not match");
                this.showInvalidPasswordError("Passowrds do not match");
                isValid = false;
            }

            return isValid;
        },

        removeValidation: function() {
            // var form = $("#registerForm");
            // form.removeClass("was-validated");
            this.$refs.registerForm.removeValidation();

            this.removeInvalidNameError();
            this.removeInvalidSurnameError();
            this.removeInvalidUserNameError();
            this.removeInvalidDateError();
            this.removeInvalidPasswordError();
            this.removeInvalidRepPasError();
        },

        registerCustomer: function() {
            if (this.validateForm()) {
                this.newCustomer.dateOfBirth += " 08:00:00";

                axios
                    .post("/api/authentication/registerCustomer", this.newCustomer)
                    .then(response => {
                        const token = response.data.token;
                        const role = response.data.role;

                        localStorage.setItem("token", token);
                        localStorage.setItem("role", role);

                        // make axios send token as default header
                        axios.defaults.headers.common["Authorization"] =
                            "Bearer " + token;

                        this.$root.redirectToUserPage();
                    })
                    .catch(error => {
                        console.log(error.response.data);
                        if (error.response.data === "Username " + this.newCustomer.username + " is taken.") {
                            this.showInvalidUserNameError(error.response.data);
                        } else if (error.response.data.startsWith("Unparseable date:")) {
                            this.showInvalidDateError("Date must be in format " + this.$root.getDateFormat());
                        } else {
                            this.$root.defaultCatchError(error);
                        }
                    });
            }
        }
    },

    mounted() {
        this.newCustomer.gender = this.Genders.MALE;
    },

    destroyed() {}
});
