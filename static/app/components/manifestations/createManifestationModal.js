Vue.component("createManifestationModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Create Manifestation"
        successBtnText="Create Manifestation"
        cancelBtnText="Cancel"

        v-on:successEvent="createManifestation"
        v-on:cancelEvent="cancel"
    >
        <h1>Create manifestation modal</h1>
    </baseModal>
    `,

    props: {
        id: String
    },

    data: function() {
        return {};
    },

    methods: {
        createManifestation: function() {
            console.log("Nemanja");
            this.closeModal();
        },

        cancel: function() {
            console.log("Nemanja");
            this.closeModal();
            // this.$emit('cancelEvent', event)
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {


            this.removeValidation();
        },

        removeValidation: function() {
            // var form = $("#addSalesmanForm");
            // form.removeClass("was-validated");

            // this.$refs.addSalesmanForm.removeValidation();

            // this.removeInvalidNameError();
            // this.removeInvalidSurnameError();
            // this.removeInvalidUserNameError();
            // this.removeInvalidDateError();
            // this.removeInvalidPasswordError();
            // this.removeInvalidRepPasError();
        },
    },

    mounted() {},

    destroyed() {}
});
