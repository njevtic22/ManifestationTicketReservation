Vue.component("tickets", {
    template: `
    <div>
        <div class="row">        
            <div class="form-group col-md-9">
                <div class="col d-flex justify-content-between">
                    <pageSizeSelect
                        name="sizeInput"
                        v-bind:value="sizeStr"
                        v-bind:options="sizes"
                        v-bind:page="page"
                        v-bind:size="size"
                        v-bind:currentDataSize="tickets.data.length"
                        v-bind:totalNumberOfResults="tickets.totalNumberOfResults"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    >
                    </pageSizeSelect>

                    <pagination
                        v-bind:currentPage="page"
                        v-bind:hasPrevious="tickets.hasPreviousPage"
                        v-bind:hasNext="tickets.hasNextPage"

                        v-on:previous="previousPage"
                        v-on:next="nextPage"
                        v-on:to="toPage($event)"
                    >
                    </pagination>
                </div>

                <br/>


                <div class="d-flex justify-content-center" v-if="loading">
                    <div class="spinner-grow text-secondary" role="status" style="width: 3rem; height: 3rem;">
                        <span class="sr-only">Loading...</span>
                    </div>
                </div>


                <ticketsTable v-else
                    v-bind:tickets="tickets.data"
                    v-on:sort="performSort($event)"
                    v-on:deleteTicket="confirmDeleteTicket($event)"

                    ref="ticketsTable"
                >
                </ticketsTable>
            </div>
        </div>

        <deleteTicketModal
            id="deleteTicketModal"
            ref="deleteTicketModal"

            v-bind:ticket="ticketToDelete"
            v-on:deleteTicket="deleteTicket($event)"
        >
        </deleteTicketModal>

        <ticketService ref="ticketService"></ticketService>
    </div>
    `,

    data: function() {
        return {
            loading: true,
            tickets: {
                data: [],
                totalNumberOfResults: 0,
                hasNextPage: null,
                hasPreviousPage: null
            },

            ticketToDelete: {
                id: 0,
                appId: "0000000000",
                price: 0,
                status: "FREE",
                type: "REGULAR",

                customer: "",
                manifestation: "",
                manifestationId: 0
            },

            page: 0,
            size: 10,
            sizeStr: "10",
            sizes: [
                "10",
                "20",
                "50",
                "100",
                "200",
                "500",
                "All"
            ],
            
            sortBy: "appId",
            sortOrder: "asc",

            searchData: {

            },
            filterData: {

            }
        };
    },

    methods: {
        previousPage: function() {
            this.page--;
            this.getTickets();
        },
        
        nextPage: function() {
            this.page++;
            this.getTickets();
        },

        toPage: function(to) {
            this.page = to;
            this.getTickets();
        },

        performSort: function(sortDetails) {
            this.sortBy = sortDetails.sortBy;
            this.sortOrder = sortDetails.sortOrder;
            this.getTickets();
        },

        confirmDeleteTicket: function(ticket) {
            this.ticketToDelete = ticket;
            $("#deleteTicketModal").modal("show");
        },

        deleteTicket: function(ticketId) {
            const successCallback = (response) => {
                this.$root.successToast("Ticket is deleted");
                this.getTickets();
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.ticketService.deleteTicket(
                ticketId,
                successCallback,
                errorCallback
            );
        },

        changeSize: function(event) {
            this.page = 0;
            this.sizeStr = event;
            if (event === "All") {
                this.size = Infinity;
            } else {
                this.size = Number(event);
            }
            this.getTickets();
        },

        submitSearchFilterSort: function(searchFilterSortEvent) {
            
            // const myData = JSON.parse(JSON.stringify(searchFilterSortEvent));

            // this.searchData = myData.searchData;
            // this.filterData = myData.filterData;
            // this.sortBy = myData.sortBy;
            // this.sortOrder = myData.sortOrder;

        
            // if (this.searchData.searchDateFrom)
            //     this.searchData.searchDateFrom += " 00:00:00";
            // if (this.searchData.searchDateTo)
            //     this.searchData.searchDateTo += " 23:59:59";

            console.log("submitSearchFilterSort");
            
            this.getTickets();
        },

        resetSearchFilterSort: function() {
            // this.sortBy = "date";
            // this.sortOrder = "asc";

            // this.searchData = {
            //     searchName: "",
            //     searchCity: "",
            //     searchStreet: "",

            //     searchDateFrom: "",
            //     searchDateTo: "",
                
            //     searchPriceFrom: 0,
            //     searchPriceTo: 0
            // },
            // this.filterData = {
            //     filterType: "",
            //     filterAvailable: ""
            // },

            console.log("resetSearchFilterSort");

            this.getTickets();
        },

        getTickets: function() {
            this.loading = true;
            this.tickets = {
                data: [],
                totalNumberOfResults: 0,
                hasNextPage: null,
                hasPreviousPage: null
            };

            const successCallback = (response) => {
                this.tickets = response.data;
                this.loading = false;
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.ticketService.getTickets(
                this.page,
                this.sizeStr,
                this.sortBy,
                this.sortOrder,
                this.searchData,
                this.filterData,
                successCallback,
                errorCallback
            )
        }
    },

    mounted() {
        this.getTickets();
    },

    destroyed() {}
});
