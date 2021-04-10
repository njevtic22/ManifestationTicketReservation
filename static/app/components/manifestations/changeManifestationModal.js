Vue.component("changeManifestationModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Change Manifestation"
        modalClass="modal-lg"
        successBtnText="Apply"
        cancelBtnText="Cancel"

        v-on:successEvent="changeManifestation"
        v-on:cancelEvent="cancel"
    >
        <h1>{{ manifestation.name }}</h1>
    </baseModal>
    `,

    props: {
        id: String,
        manifestation: {
            type: Object,
            required: true
        }
    },

    data: function() {
        return {};
    },

    methods: {
        changeManifestation: function() {
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
