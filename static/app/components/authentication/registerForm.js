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
            
                <div>
                    {{ JSON.stringify(newCustomer) }}
                </div>
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
            pasRepErrorMessage: "Password must not be repeated",
            dateErrorMessage: "Date must not be empty",

            isNameInvalid: false,
            isSurnameInvalid: false,
            isUsernameInvalid: false,
            isPasswordInvalid: false,
            isPasRepInvalid: false,
            isDateInvalid: false
            
        };
    },

    methods: {
        registerCustomer: function() {
            

            this.newCustomer.dateOfBirth += " 08:00:00";
            // axios
            //     .post("/api/authentication/registerCustomer", this.newCustomer)
            //     .then(response => {
            //         console.log(response);
            //     })
            //     .catch(err => {
            //         this.$root.defaultCatchError(err);
            //     });
        }
    },

    mounted() {
        this.newCustomer.gender = this.Genders.MALE;
    },

    destroyed() {}
});
