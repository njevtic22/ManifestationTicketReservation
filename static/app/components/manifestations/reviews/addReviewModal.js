Vue.component("addReviewModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Add review"
        modalClass=""
        successBtnText="Add review"
        cancelBtnText="Cancel"

        v-on:successEvent="addReview"
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
        addReview: function() {
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
