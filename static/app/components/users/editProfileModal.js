Vue.component("editProfileModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        modalTitle="Change profile"
        successBtnText="Apply"
        btnSuccessClass="btn-success"
        cancelBtnText="Cancel"

        v-on:successEvent="changeProfile"
        v-on:cancelEvent="cancel"
    >
        <h1>Edit profile modal</h1>

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

        changeProfile: function() {
            this.closeModal();
            this.$emit('changedProfile')
        },

        cancel: function() {
            this.closeModal();
            // this.$emit('cancelEvent', event)
        }
    },

    mounted() {},

    destroyed() {}
});
