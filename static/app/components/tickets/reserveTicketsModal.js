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
        <h4>Reserve tickets for: {{manifestationName}}</h4>
        <hr/>
            {{ customerType }}
            <br/>
            {{ customerDiscount }}
        <hr/>
        <div class='row' style="margin-left: 1px">
            <div class="col-md-4"> 
                <div class="row"><br/></div>
                <div class="row">Regular tickets:</div>
                <div class="row">Fan pit tickets:</div>
                <div class="row">VIP tickets:</div>

            </div>
            <div class="col-md-4"> 
                <div class="row">Left</div>
                <div class="row">{{ regularLeft }}</div>
                <div class="row">{{ fanLeft }}</div>
                <div class="row">{{ vipLeft }}</div>

            </div>
            <div class="col-md-4"> 
                <div class="row">Price</div>
                <div class="row">{{ regularPrice }} RSD</div>
                <div class="row">{{ fanPrice }} RSD</div>
                <div class="row">{{ vipPrice }} RSD</div>

            </div>
        </div>
        <hr/>

        <baseForm
            id="reserveTicketsForm"
            ref="reserveTicketsForm"
        >
            <div class="form-group row">
                <label for="regularInput" class="col-sm-6 col-form-label">Number of regular tickets:</label>
                <numberInput
                    class="col-sm-6"
                    name="regularInput"
                    v-model="ticketsToReserve.numberOfRegularTickets"
                    v-bind:max="regularLeft"
                    required
                >
                </numberInput>
            </div>

            <div class="form-group row">
                <label for="fanPitInput" class="col-sm-6 col-form-label">Number of fan pit tickets:</label>
                <numberInput
                    class="col-sm-6"
                    name="fanPitInput"
                    v-model="ticketsToReserve.numberOfFanPitTickets"
                    v-bind:max="fanLeft"
                    required
                >
                </numberInput>
            </div>
            
            <div class="form-group row">
                <label for="vipInput" class="col-sm-6 col-form-label">Number of vip tickets:</label>
                <numberInput
                    class="col-sm-6"
                    name="vipInput"
                    v-model="ticketsToReserve.numberOfVIPTickets"
                    v-bind:max="vipLeft"
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
        },

        regularLeft: {
            type: Number,
            required: true
        },
        fanLeft: {
            type: Number,
            required: true
        },
        vipLeft: {
            type: Number,
            required: true
        },

        regularPrice: {
            type: Number,
            required: true
        },
        fanPrice: {
            type: Number,
            required: true
        },
        vipPrice: {
            type: Number,
            required: true
        }
    },

    data: function() {
        return {
            ticketsToReserve: {
                numberOfRegularTickets: 0,
                numberOfFanPitTickets: 0,
                numberOfVIPTickets: 0
            },

            customerType: "",
            customerDiscount: ""
        };
    },

    methods: {
        reserveTickets: function() {
            const requestBody = {
                manifestationId: this.manifestationId,
                numberOfRegularTickets: this.ticketsToReserve.numberOfRegularTickets,
                numberOfFanPitTickets: this.ticketsToReserve.numberOfFanPitTickets,
                numberOfVIPTickets: this.ticketsToReserve.numberOfVIPTickets
            };

            const successCallback = (response) => {
                this.closeModal();
                this.$emit("ticketsReserved");
                this.$root.successToast("Tickets are reserved");
            };
            const errorcallback = (error) => {
                const expectedError = "Manifestation does not have enough ";
                
                const msg = error.response.data;
                const status = error.response.status;

                if (status == 400) {
                    if (msg.startsWith(expectedError)) {
                        this.$root.successToast(msg);
                    }
                } else {
                    this.$root.defaultCatchError(error); 
                }
            };
            
            // this.$refs.ticketService.reserveTickets(
            //     requestBody,
            //     successCallback,
            //     errorcallback
            // );
            console.log(requestBody);
        },

        cancel: function() {
            this.closeModal();
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {
            this.ticketsToReserve = {
                numberOfRegularTickets: 0,
                numberOfFanPitTickets: 0,
                numberOfVIPTickets: 0
            };
        }
    },

    mounted() {
        this.customerType = localStorage.getItem("customerType");
        this.customerDiscount = localStorage.getItem("customerDiscount");
    },

    destroyed() {}
});
