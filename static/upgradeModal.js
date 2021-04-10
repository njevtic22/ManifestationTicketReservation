Vue.component("upgreadeModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="CHANGE THIS"
        modalClass="modal-lg"
        successBtnText="CHANGE THIS"
        cancelBtnText="Cancel"

        v-on:successEvent="CHANGETHIS"
        v-on:cancelEvent="cancel"
    >
        <h1>Upgrade modal</h1>
    </baseModal>
    `,

    props: {
        id: String,
    },

    data: function() {
        return {};
    },

    methods: {
        CHANGETHIS: function() {
            this.closeModal();
        },

        cancel: function() {
            this.closeModal();
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {

        }
    },

    mounted() {},

    destroyed() {}
});
