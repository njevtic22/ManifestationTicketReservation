Vue.component("addTicketsModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Add tickets"
        modalClass=""
        successBtnText="Add tickets"
        cancelBtnText="Cancel"

        v-on:successEvent="addTickets"
        v-on:cancelEvent="cancel"
    >
        <h4>Add tickets for: {{manifestationName}}</h4>
        <hr/>
        <baseForm 
            id="addTicketsForm"
            ref="addTicketsForm"
        >
            <div class="form-group row">
                <label for="regularInput" class="col-sm-6 col-form-label">Number of regular tickets:</label>
                <numberInput
                    class="col-sm-6"
                    name="regularInput"
                    v-model="ticketToAdd.numberOfRegularTickets"
                    required
                >
                </numberInput>
            </div>

            <div class="form-group row">
                <label for="fanPitInput" class="col-sm-6 col-form-label">Number of fan pit tickets:</label>
                <numberInput
                    class="col-sm-6"
                    name="fanPitInput"
                    v-model="ticketToAdd.numberOfFanPitTickets"
                    required
                >
                </numberInput>
            </div>
            
            <div class="form-group row">
                <label for="vipInput" class="col-sm-6 col-form-label">Number of vip tickets:</label>
                <numberInput
                    class="col-sm-6"
                    name="vipInput"
                    v-model="ticketToAdd.numberOfVIPTickets"
                    required
                >
                </numberInput>
            </div>
        </baseForm>

        <ticketService ref="ticketService"></ticketService>
    </baseModal>
    `,

    props: {
        id: String,
        manifestationId: {
            type: Number,
            required: true
        },
        manifestationName: {
            type: String,
            required: true
        }
    },

    data: function() {
        return {
            ticketToAdd: {
                numberOfRegularTickets: 0,
                numberOfFanPitTickets: 0,
                numberOfVIPTickets: 0
            },
        };
    },

    methods: {
        addTickets: function() {
            const requestBody = {
                manifestationId: this.manifestationId,
                numberOfRegularTickets: this.ticketToAdd.numberOfRegularTickets,
                numberOfFanPitTickets: this.ticketToAdd.numberOfFanPitTickets,
                numberOfVIPTickets: this.ticketToAdd.numberOfVIPTickets
            }
            
            const successCallback = (response) => {
                this.closeModal();
                this.$emit("addedTickets");
                this.$root.successToast("Tickets are added");
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };
            
            this.$refs.ticketService.addTickets(
                requestBody,
                successCallback,
                errorCallback
            );
        },

        cancel: function() {
            this.closeModal();
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {
            this.ticketToAdd = {
                numberOfRegularTickets: 0,
                numberOfFanPitTickets: 0,
                numberOfVIPTickets: 0
            };
        }
    },

    mounted() {
    },

    destroyed() {}
});
