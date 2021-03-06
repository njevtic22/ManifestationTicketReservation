Vue.component("changePasswordModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        modalTitle="Change password"
        successBtnText="Apply"
        btnSuccessClass="btn-success"
        cancelBtnText="Cancel"

        v-on:successEvent="changePassword"
        v-on:cancelEvent="cancel"
    >
        <baseForm 
            id="changePasswordForm"
            ref="changePasswordForm"
        >
            <div class="form-group row">
                <label for="inputOldPas" class="col-sm-2 col-form-label">Old Password</label>
                <passwordInput
                    id="inputOldPas"
                    class="col-sm-10"
                    name="oldPassword"
                    placeholder="Old password"
                    v-model="passwordData.oldPassword"
                    v-bind:errorMessage="oldPasswordErrorMessage"
                    v-bind:isInvalid="isOldPasswordInvalid"
                    required
                >
                </passwordInput>
            </div>

            <div class="form-group row">
                <label for="inputNewPas" class="col-sm-2 col-form-label">New password</label>
                <passwordInput
                    id="inputNewPas"
                    class="col-sm-10"
                    name="newPassword"
                    placeholder="New password"
                    v-model="passwordData.newPassword"
                    v-bind:errorMessage="newPasswordErrorMessage"
                    v-bind:isInvalid="isNewPasswordInvalid"
                    required
                >
                </passwordInput>
            </div>

            <div class="form-group row">
                <label for="inputRepPas" class="col-sm-2 col-form-label">Repeat password</label>
                <passwordInput
                    id="inputRepPas"
                    class="col-sm-10"
                    name="repPas"
                    placeholder="Repeat password"
                    v-model="passwordData.repPassword"
                    v-bind:errorMessage="pasRepErrorMessage"
                    v-bind:isInvalid="isPasRepInvalid"
                    required
                >
                </passwordInput>
            </div>
        </baseForm>

        <authenticationService ref="authService"></authenticationService>
    </baseModal>
    `,

    props: {
        id: String,
        userId: Number,
    },

    data: function() {
        return {
            passwordData: {
                oldPassword: "",
                newPassword: "",
                repPassword: ""
            },
            
            oldPasswordErrorMessage: "Old password must not be empty",
            newPasswordErrorMessage: "New password must not be empty",
            pasRepErrorMessage: "Password must be repeated",

            isOldPasswordInvalid: false,
            isNewPasswordInvalid: false,
            isPasRepInvalid: false,
        };
    },

    methods: {
        showInvalidOldPasswordError: function(message) {
            this.oldPasswordErrorMessage = message;
            this.isOldPasswordInvalid = true;
        },

        removeInvalidOldPasswordError: function() {
            this.oldPasswordErrorMessage = "Old password must not be empty";
            this.isOldPasswordInvalid = false;
        },

        showInvalidNewPasswordError: function(message) {
            this.newPasswordErrorMessage = message;
            this.isNewPasswordInvalid = true;
        },

        removeInvalidNewPasswordError: function() {
            this.newPasswordErrorMessage = "Old password must not be empty";
            this.isNewPasswordInvalid = false;
        },

        showInvalidRepPasError: function(message) {
            this.pasRepErrorMessage = message;
            this.isPasRepInvalid = true;
        },

        removeInvalidRepPasError: function() {
            this.pasRepErrorMessage = "Password must be repeated";
            this.isPasRepInvalid = false;
        },

        validateForm: function() {
            // var form = $("#changePasswordForm");
            // // var form = document.getElementById("changePasswordForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();

            var isValid = this.$refs.changePasswordForm.validateForm();

            if (this.passwordData.newPassword !== this.passwordData.repPassword) {
                this.showInvalidRepPasError("Passowrds do not match");
                this.showInvalidNewPasswordError("Passowrds do not match");
                isValid = false;
            }

            return isValid;
        },

        removeValidation: function() {
            // var form = $("#changePasswordForm");
            // form.removeClass("was-validated");
            this.$refs.changePasswordForm.removeValidation();

            this.removeInvalidOldPasswordError();
            this.removeInvalidNewPasswordError();
            this.removeInvalidRepPasError();
        },

        clearModal: function() {
            this.passwordData.oldPassword = "";
            this.passwordData.newPassword = "";
            this.passwordData.repPassword = "";

            this.removeValidation();
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        changePassword: function() {
            if (this.validateForm()) {
                this.removeValidation();

                const successCallback = (response) => {
                    this.closeModal();
                    this.$emit('changedPassword');
                    this.$root.successToast("Password changed");
                };

                const errorCallback = (error) => {
                    console.log(error.response.data);
                    if (error.response.data === "Wrong password") {
                        this.showInvalidOldPasswordError(error.response.data);
                    } else if (error.response.data === "Passwords do not match") {
                        this.showInvalidNewPasswordError(error.response.data);
                        this.showInvalidRepPasError(error.response.data);
                    } else {
                        this.$root.defaultCatchError(error);
                    }
                };
                
                this.$refs.authService.changePassword(this.userId, this.passwordData, successCallback, errorCallback);
            }
        },

        cancel: function() {
            this.closeModal();
            // this.$emit('cancelEvent', event)
        }
    },

    mounted() {},

    destroyed() {}
});
