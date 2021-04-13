Vue.component("deleteTicketModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-danger"
        btnSuccessClass="btn-danger"
        modalTitle="Delete ticket"
        modalClass=""
        successBtnText="Delete"
        cancelBtnText="Cancel"

        v-on:successEvent="deleteTicket"
        v-on:cancelEvent="cancel"
    >
        <div class='row' style="margin-left: 1px">
            <div class="col-md-6"> 
                <div class="row">Ticket ID:</div>
                <div class="row">Price:</div>
                <div class="row">Status:</div>
                <div class="row">Type:</div>
                <div class="row">Manifestation:</div>
                <div class="row">Customer:</div>
            </div>
            <div class="col-md-6"> 
                <div class="row">{{ ticket.appId }}</div>
                <div class="row">{{ ticket.price }} RSD</div>
                <div class="row">{{ ticket.status }}</div>
                <div class="row">{{ ticket.type }}</div>
                <div class="row">{{ ticket.manifestation }}</div>
                <div class="row">{{ ticket.customer ? ticket.customer : '/' }}</div>
            </div>
        </div>
        
        <hr/>
        Are you sure you want to permanently delete this ticket?
        <br/>
        <strong>This action can not be undode.</strong>
    </baseModal>
    `,

    props: {
        id: String,
        ticket: {
            type: Object,
            required: true
        }
    },

    data: function() {
        return {};
    },

    methods: {
        deleteTicket: function() {
            this.closeModal();
            this.$emit('deleteTicket', this.ticket.id);
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
