Vue.component("historiesTable", {
    template: `
    <table border="5" class="table table-light table-striped table-hover table-borderless">
        <thead class="thead-dark">
            <tr>
                <th>
                    <caret-up-square-fill-icon v-if="sortTicketIdAsc" v-on:click="sortTicketId('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortTicketId('asc')"></caret-down-square-fill-icon>
                    Ticket ID
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortWithdrawalDateAsc" v-on:click="sortHistoryDate('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortHistoryDate('asc')"></caret-down-square-fill-icon>
                    Withdrawal date
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortTicketPriceAsc" v-on:click="sortPrice('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortPrice('asc')"></caret-down-square-fill-icon>
                    Ticket price
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortTicketTypeAsc" v-on:click="sortType('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortType('asc')"></caret-down-square-fill-icon>
                    Ticket type
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortManAsc" v-on:click="sortMan('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortMan('asc')"></caret-down-square-fill-icon>
                    Manifestation
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortManTypeAsc" v-on:click="sortManType('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortManType('asc')"></caret-down-square-fill-icon>
                    Manifestation type
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortManStatusAsc" v-on:click="sortManStatus('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortManStatus('asc')"></caret-down-square-fill-icon>
                    Manifestation status
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortManDateAsc" v-on:click="sortManDate('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortManDate('asc')"></caret-down-square-fill-icon>
                    Manifestation date
                </th>
            </tr>
        </thead>
        <tbody v-if="histories.length !== 0">
            <tr v-for="history in histories">
                <td>{{ history.ticketId }}</td>
                <td>{{ history.withdrawalDate }}</td>
                <td>{{ history.price }}</td>
                <td>{{ history.ticketType }}</td>
                <td>{{ history.manifestation }}</td>
                <td>{{ history.manifestationType }}</td>
                <td>{{ history.manifestationStatus }}</td>
                <td>{{ history.manifestationDate }}</td>
            </tr>
        </tbody>

        <tbody v-else-if="loading">
            <tr>
                <td colspan="8">
                    <h4 class="text-center">
                    
                        <div class="d-flex justify-content-center" v-if="loading">
                            <div class="spinner-grow text-secondary" role="status" style="width: 3rem; height: 3rem;">
                                <span class="sr-only">Loading...</span>
                            </div>
                        </div>

                    </h4>
                </td>
            </tr>
        </tbody>

        <tbody v-else>
            <tr>
                <td colspan="8"><h4 class="text-center">No Results</h4></td>
            </tr>
        </tbody>
    </table>
    `,

    props: {
        loading: {
            type: Boolean,
            required: true
        },
        histories: {
            type: Array,
            required: true
        }
    },

    data: function() {
        return {
            sortTicketIdAsc: true,
            sortWithdrawalDateAsc: true,
            sortTicketPriceAsc: true,
            sortTicketTypeAsc: true,

            sortManAsc: true,
            sortManTypeAsc: true,
            sortManStatusAsc: true,
            sortManDateAsc: true 
        };
    },

    methods: {
        resetSort: function() {
            this.sortTicketIdAsc = true;
            this.sortWithdrawalDateAsc = true;
            this.sortTicketPriceAsc = true;
            this.sortTicketTypeAsc = true;

            this.sortManAsc = true;
            this.sortManTypeAsc = true;
            this.sortManStatusAsc = true;
            this.sortManDateAsc = true;
        },

        sortTicketId: function(sortOrder) {
            this.resetSort();
            this.sortTicketIdAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "ticketAppId", sortOrder: sortOrder });
        },

        sortHistoryDate: function(sortOrder) {
            this.resetSort();
            this.sortWithdrawalDateAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "historyDate", sortOrder: sortOrder });
        },

        sortPrice: function(sortOrder) {
            this.resetSort();
            this.sortTicketPriceAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "price", sortOrder: sortOrder });
        },

        sortType: function(sortOrder) {
            this.resetSort();
            this.sortTicketTypeAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "ticketType", sortOrder: sortOrder });
        },
        
        sortMan: function(sortOrder) {
            this.resetSort();
            this.sortManAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "manifestation", sortOrder: sortOrder });
        },

        sortManType: function(sortOrder) {
            this.resetSort();
            this.sortManTypeAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "manifestationType", sortOrder: sortOrder });
        },

        sortManStatus: function(sortOrder) {
            this.resetSort();
            this.sortManStatusAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "manifestationStatus", sortOrder: sortOrder });
        },

        sortManDate: function(sortOrder) {
            this.resetSort();
            this.sortManDateAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "manifestationDate", sortOrder: sortOrder });
        },
    },

    mounted() {},

    destroyed() {}
});
