Vue.component("ticketService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseUrl: "/api/tickets"

            /**
             * 
             * 
                post("", add);
                post("/reserve/:id", reserve);
                post("/withdraw/:id", withdraw);
                get("", getAll, new GetAllTicketsTransformer(gson, new GetAllTicketsMapper(formatter)));
                delete("/:id", delete);
             * 
             * 
             */
        };
    },

    methods: {
        addTickets: function(ticketsToAdd, successCallback, errorCallback) {
            axios
                .post(this.baseUrl, ticketsToAdd)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
