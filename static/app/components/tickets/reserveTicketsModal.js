Vue.component("reserveTicketsModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Reserve tickets"
        modalClass=""
        successBtnText="Reserve"
        cancelBtnText="Cancel"

        v-on:successEvent="reserveTickets"
        v-on:cancelEvent="cancel"
    >
        <h1>reserveTicketsModal modal</h1>
    </baseModal>
    `,

    props: {
        id: String,
    },

    data: function() {
        return {};
    },

    methods: {
        reserveTickets: function() {
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
