Vue.component("loginForm", {
    template: `
    <baseForm 
        id="loginForm" 
        class="login-center text-center"
        ref="loginForm"
    >
        <img src="/images/Cloud Logo Wide.png" alt="No image" width="280" height="210"/>
        <h3>Please log in</h3>
        <hr>

        <textInput
            name="username"
            v-model="username"
            v-bind:errorMessage="userNameErrorMessage"
            v-bind:isInvalid="isUsernameInvalid"
            componentClass="form-group"
            placeholder="Username"
            required
        >
        </textInput>
        <passwordInput
            name="password"
            v-model="password"
            v-bind:errorMessage="passwordErrorMessage"
            v-bind:isInvalid="isPasswordInvalid"
            componentClass="form-group"
            placeholder="Password"
            required
        >
        </passwordInput>


        <br>
        <button type="button" value="Log in" v-on:click="login" class="btn btn-lg btn-primary btn-block">Log in</button>	
        <p class="mt-5 mb-3 text-muted">© Manifestation service</p>


        <authenticationService ref="authService"></authenticationService>

        <!--
        <button type="button" value="Log in" v-on:click="failureToast" class="btn btn-lg btn-danger btn-block">Failure toast</button>	
        <button type="button" value="Log in" v-on:click="successToast" class="btn btn-lg btn-success btn-block">Success toast</button>	
        <button type="button" value="Log in" v-on:click="infoToast" class="btn btn-lg btn-primary btn-block">Info toast</button>	
        -->
    </baseForm>
    `,

    data: function() {
        return {
            username: "",
            password: "",

            userNameErrorMessage: "Username must not be empty",
            passwordErrorMessage: "Password must not be empty",

            isUsernameInvalid: false,
            isPasswordInvalid: false
        };
    },

    methods: {
        // failureToast: function() {
        //     this.$root.$emit("toastFailure", "This toast failed");
        // },

        // successToast: function() {
        //     this.$root.$emit("toastSuccess", "This toast succeeded");
        // },

        // infoToast: function() {
        //     this.$root.$emit("toastInfo", "This toast informed");
        // },

        showInvalidUserNameError: function(message) {
            this.userNameErrorMessage = message;
            this.isUsernameInvalid = true;
        },

        removeInvalidUserNameError: function() {
            this.userNameErrorMessage = "Username must not be empty";
            this.isUsernameInvalid = false;
        },

        showInvalidPasswordError: function(message) {
            this.passwordErrorMessage = message;
            this.isPasswordInvalid = true;
        },

        removeInvalidPasswordError: function() {
            this.passwordErrorMessage = "Password must not be empty";
            this.isPasswordInvalid = false;
        },

        validateForm: function() {
            // var form = $("#loginForm");
            // // var form = document.getElementById("loginForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();
            return this.$refs.loginForm.validateForm();
        },

        removeValidation: function() {
            // var form = $("#loginForm");
            // form.removeClass("was-validated");
            this.$refs.loginForm.removeValidation();

            this.removeInvalidUserNameError();
            this.removeInvalidPasswordError();
        },

        login: function() {
            if (this.validateForm()) {
                this.removeValidation();

                const userData = {
                    username: this.username,
                    password: this.password
                };

                this.$refs.authService.login(
                    userData,
                    (response) => {
                        const token = response.data.token;
                        const role = response.data.role;

                        localStorage.setItem("token", token);
                        localStorage.setItem("role", role);

                        // make axios send token as default header
                        axios.defaults.headers.common["Authorization"] =
                            "Bearer " + token;

                        this.$root.redirectToUserPage();
                    },
                    (error) => {
                        if (error.response.data == "Invalid username") {
                            this.showInvalidUserNameError(error.response.data);
                        } else if (error.response.data == "Invalid password") {
                            this.showInvalidPasswordError(error.response.data);
                        } else {
                            this.$root.defaultCatchError(error);
                        }
                    }    
                )
            }
        }

        // toggleSidebar: function() {
        //     $("#wrapper").toggleClass("toggled");
        // }
    },

    mounted() {
    },

    destroyed() {}
});
