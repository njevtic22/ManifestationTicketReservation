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
        <h1>Change password modal</h1>

        <userService ref="userService"></userService>
    </baseModal>
    `,

    props: {
        id: String
    },

    data: function() {
        return {};
    },

    methods: {
        clearModal: function() {

        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        changePassword: function() {
            this.closeModal();
            this.$emit('changedPassword')
        },

        cancel: function() {
            this.closeModal();
            // this.$emit('cancelEvent', event)
        }
    },

    mounted() {},

    destroyed() {}
});
