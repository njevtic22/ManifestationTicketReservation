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
        formSearchFilterUrl: function(searchFilterData) {
            let url = "";
            for (const key of Object.keys(searchFilterData)) {
                if (searchFilterData[key]) {
                    url += key + "=" + searchFilterData[key] + "&";
                }
            }

            // Remove trailing &
            if (url.length != 0) {
                url = url.slice(0, -1);
            }
            return url;
        },

        getTickets: function(page, size, sortBy, sortOrder, searchData, filterData, successCallback, errorCallback) {
            const filterUrl = this.formSearchFilterUrl(filterData);
            const searchUrl = this.formSearchFilterUrl(searchData);

            let url = `${this.baseUrl}?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}`;
            if (searchUrl.length != 0)
                url += `&${searchUrl}`;
            if (filterUrl.length != 0)
                url += `&${filterUrl}`;

            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

        addTickets: function(ticketsToAdd, successCallback, errorCallback) {
            axios
                .post(this.baseUrl, ticketsToAdd)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

        reserveTickets: function(ticketsToReserve, successCallback, errorCallback) {
            const url = `${this.baseUrl}/reserve/${ticketsToReserve.manifestationId}`;
            axios
                .post(url, ticketsToReserve)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

        deleteTicket: function(ticketId, successCallback, errorCallback) {
            const url = `${this.baseUrl}/${ticketId}`;
            axios
                .delete(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
