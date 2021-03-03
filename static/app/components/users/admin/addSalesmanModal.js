Vue.component("addSalesmanModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        modalTitle="Add Salesman"
        successBtnText="Add salesman"
        cancelBtnText="Cancel"

        v-on:successEvent="success"
        v-on:cancelEvent="cancel"
    >
        <h1>Nemanja</h1>

        
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
        success: function(event) {
            $("#" + this.id).modal("hide");
            this.$emit('successEvent', event)
        },

        cancel: function(event) {
            $("#" + this.id).modal("hide");
            this.$emit('cancelEvent', event)
        }
    },

    mounted() {},

    destroyed() {}
});
