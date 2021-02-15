Vue.component("logInPage", {
    template: `
    <div class="d-flex" id="wrapper" v-bind:class="{toggled: isToggled}">

        <!-- Sidebar -->
        <div class="bg-light border-right" id="sidebar-wrapper">
            <anonymousSidebar></anonymousSidebar>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
                <button class="btn btn-outline-light" id="menu-toggle" v-on:click="toggleSidebar">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-ul" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm-3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                    </svg>
                </button>

                <h5 class="px-3 text-light pageTitle">{{$route.meta.title}}</h5>
            </nav>

            <div class="container-fluid">
                <main role="main">

                    <div class="login-center text-center">
                        <form 
                            id="loginForm" 
                            name="Login form"
                        >
                            <img src="/images/Cloud Logo Wide.png" alt="No image" width="280" height="210"/>
                            <h3>Please log in</h3>
                            <hr>

                            <textInput
                                name="username"
                                v-model="username"
                                v-bind:errorMessage="userNameErrorMessage"
                                v-bind:isInvalid="isUsernameInvalid"
                                placeholder="Username"
                                required
                            >
                            </textInput>
                            <passwordInput
                                name="password"
                                v-model="password"
                                v-bind:errorMessage="passwordErrorMessage"
                                v-bind:isInvalid="isPasswordInvalid"
                                placeholder="Password"
                                required
                            >
                            </passwordInput>


                            <br>
                            <button type="button" value="Log in" v-on:click="login($event)" class="btn btn-lg btn-primary btn-block">Log in</button>	
                            <p class="mt-5 mb-3 text-muted">© Manifestation service</p>

                            <!--
                            <button type="button" value="Log in" v-on:click="failureToast" class="btn btn-lg btn-danger btn-block">Failure toast</button>	
                            <button type="button" value="Log in" v-on:click="successToast" class="btn btn-lg btn-success btn-block">Success toast</button>	
                            <button type="button" value="Log in" v-on:click="infoToast" class="btn btn-lg btn-primary btn-block">Info toast</button>	
                            -->
                        </form>
                    </div>

                </main>
            </div>
        </div>
        <!-- /#page-content-wrapper -->

    </div>
    `,

    data: function() {
        return {
            username: "",
            password: "",

            userNameErrorMessage: "",
            passwordErrorMessage: "",

            isUsernameInvalid: false,
            isPasswordInvalid: false,

            isToggled: false
        };
    },

    methods: {
        toggleSidebar: function() {
            this.isToggled = !this.isToggled;
        },

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
            this.passwordErrorMessage = "Username must not be empty";
            this.isPasswordInvalid = false;
        },

        validateForm: function() {
            var form = $("#loginForm");
            // var form = document.getElementById("loginForm");

            form.addClass("was-validated");
            // form.classList.add("was-validated");
            return form[0].checkValidity();
        },

        removeValidation: function() {
            var form = $("#loginForm");
            form.removeClass("was-validated");

            this.removeInvalidUserNameError();
            this.removeInvalidPasswordError();
        },

        login: function(e) {
            e.preventDefault();
            if (this.validateForm()) {
                this.removeValidation();

                var userData = {
                    username: this.username,
                    password: this.password
                };
                axios
                    .post("/api/authentication/login", userData)
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
                        if (error.response.data == "Invalid username") {
                            this.showInvalidUserNameError(error.response.data);
                        } else if (error.response.data == "Invalid password") {
                            this.showInvalidPasswordError(error.response.data);
                        } else {
                            this.$root.defaultCatchError(error);
                        }
                    });
            }
        },

        // toggleSidebar: function() {
        //     $("#wrapper").toggleClass("toggled");
        // }
    },

    mounted() {
        this.userNameErrorMessage = "Username must not be empty";
        this.passwordErrorMessage = "Password must not be empty";
    },

    destroyed() {}
});
