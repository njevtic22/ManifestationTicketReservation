Vue.component("changeLocationModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Change Location"
        modalClass="modal-lg"
        successBtnText="Apply"
        cancelBtnText="Cancel"

        v-on:successEvent="changeLocation"
        v-on:cancelEvent="cancel"
    >
        <h1>{{ location.latitude }}</h1>
        <h1>{{ location.longitude }}</h1>
        
        <h1>{{ address.street }}</h1>
        <h1>{{ address.number }}</h1>
        <h1>{{ address.city }}</h1>
        <h1>{{ address.postalCode }}</h1>
    </baseModal>
    `,

    props: {
        id: String,
        location: {
            type: Object,
            required: true
        },

        address: {
            type: Object,
            required: true
        }
    },

    data: function() {
        return {};
    },

    methods: {
        changeLocation: function() {
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
