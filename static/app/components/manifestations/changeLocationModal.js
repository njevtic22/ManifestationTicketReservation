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
        
        <textInput
            name="name"
            labelText="Name"
            class="form-group"
            v-model="address.street"
            required
        >
        </textInput>
        <h1>{{ address.street }}</h1>
        <h1>{{ address.number }}</h1>
        <h1>{{ address.city }}</h1>
        <h1>{{ address.postalCode }}</h1>

        <manifestationService ref="manifestationService"></manifestationService>
    </baseModal>
    `,

    props: {
        id: String,
    },

    data: function() {
        return {
            location: {
                id: 0,
                latitude: 0,
                longitude: 0
            },
            address: {
                street: "", 
                number: 0, 
                city: "", 
                postalCode: ""
            },

            spareLocation: {
                id: 0,
                latitude: 0,
                longitude: 0
            },
            spareAddress: {
                street: "", 
                number: 0, 
                city: "", 
                postalCode: ""
            },
        };
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
            this.location = JSON.parse(JSON.stringify(this.spareLocation));
            this.address = JSON.parse(JSON.stringify(this.spareAddress));
        },
        
        getManifestation: function(manifestationId) {
            const successCallback = (response) => {
                this.location = response.data.location;
                this.address = response.data.location.address;

                this.spareLocation = JSON.parse(JSON.stringify(this.location));
                this.spareAddress = JSON.parse(JSON.stringify(this.address));
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.getManifestation(manifestationId, successCallback, errorCallback);
        },
    },

    mounted() {},

    destroyed() {}
});
