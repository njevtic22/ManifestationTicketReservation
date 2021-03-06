Vue.component("deleteUserModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-danger"
        modalTitle="Delete user"
        btnSuccessClass="btn-danger"
        successBtnText="Delete"
        cancelBtnText="Cancel"
        modalClass="modal-sm"

        v-on:successEvent="deleteUser"
        v-on:cancelEvent="cancel"
    >
        Are you sure you want to permanently delete this user?<br/><br/>
        <div class='row' style="margin-left: 1px">
            <div class="col-md-4"> 
                <div class="row">Name:</div>
                <div class="row">Surname:</div>
                <div class="row">Username:</div>
            </div>
            <div class="col-md-4"> 
                <div class="row">{{ userToDelete.name }}</div>
                <div class="row">{{ userToDelete.surname }}</div>
                <div class="row">{{ userToDelete.username }}</div>
            </div>
        </div>
        <br/>
        <strong>This action can not be undode.</strong>

        <salesmanService ref="salesmanService"></salesmanService>
        <customerService ref="customerService"></customerService>
    </baseModal>
    `,

    props: {
        id: String,
        userToDelete: {
            type: Object,
            default: {
                id: 0,
                name: "",
                surname: "",
                username: "",
                role: "CUSTOMER"
            }
        }
    },

    data: function() {
        return {};
    },

    methods: {
        deleteUser: function() {
            const successCallback = (response) => {
                this.closeModal();
                this.$emit('deletedUserEvent');
                this.$root.successToast("User is deleted");
            };

            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            if (this.userToDelete.role === "CUSTOMER") {
                this.$refs.customerService.deleteCustomer(this.userToDelete.id, successCallback, errorCallback);
            } else {
                this.$refs.salesmanService.deleteSalesman(this.userToDelete.id, successCallback, errorCallback);
            }
        },

        cancel: function() {
            this.closeModal();
            // this.$emit('cancelEvent')
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {
            // this.userToDelete = {
            //     id: 0,
            //     name: "",
            //     surname: "",
            //     username: "",
            //     role: "CUSTOMER"
            // }
        }
    },

    mounted() {},

    destroyed() {}
});
