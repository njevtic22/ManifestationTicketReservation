Vue.component("ticketsTable", {
    template: `
    <table border="5" class="table table-light table-striped table-hover table-borderless">
        <thead class="thead-dark">
            <tr>
                <!-- <th>
                    <caret-up-square-fill-icon v-if="sortIdAsc" v-on:click="sortId('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortId('asc')"></caret-down-square-fill-icon>
                    Ticket ID
                </th> -->
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
                    Holding date
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortPriceAsc" v-on:click="sortPrice('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortPrice('asc')"></caret-down-square-fill-icon>
                    Ticket price (RSD)
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortStatusAsc" v-on:click="sortStatus('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortStatus('asc')"></caret-down-square-fill-icon>
                    Ticket status
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortTypeAsc" v-on:click="sortType('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortType('asc')"></caret-down-square-fill-icon>
                    Ticket type
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortCustomerAsc" v-on:click="sortCustomer('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortCustomer('asc')"></caret-down-square-fill-icon>
                    Customer
                </th>
                <th v-if="!$root.isSalesman()">
                    Actions
                </th>
            </tr>
        </thead>
        <tbody v-if="tickets.length !== 0">
            <tr v-for="ticket in tickets">
                <!-- <td>{{ ticket.appId }}</td> -->
                <td>{{ ticket.manifestation }}</td>
                <td>{{ ticket.manifestationType }}</td>
                <td>{{ ticket.manifestationStatus }}</td>
                <td>{{ ticket.holdingDate }}</td>
                <td>{{ ticket.price }}</td>
                <td>{{ ticket.status }}</td>
                <td>{{ ticket.type }}</td>
                <td>{{ ticket.customer ? ticket.customer : '/'}}</td>

                <td class="text-center" v-if="$root.isAdmin()">
                    <button 
                        type="button" 
                        class="btn btn-link btn-sm"
                        v-if="ticket.status === 'FREE'"

                        v-on:click="$emit('deleteTicket', ticket)"
                    >
                        <trash-fill-icon></trash-fill-icon>
                    </button>
                </td>
                <td v-else-if="$root.isCustomer()">
                    <button 
                        type="button" 
                        class="btn btn-link btn-sm"
                        v-if="ticket.daysToManLeft >= 7"

                        v-on:click="$emit('withdrawTicket', ticket)"
                    >
                        <trash-fill-icon></trash-fill-icon>
                    </button>
                </td>
            </tr>
        </tbody>

        <tbody v-else-if="loading">
            <tr>
                <td v-bind:colspan="colspan">
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
                <td v-bind:colspan="colspan"><h4 class="text-center">No Results</h4></td>
            </tr>
        </tbody>
    </table>
    `,

    props: {
        tickets: Array,
        loading: Boolean
    },

    computed: {
        colspan() {
            return this.$root.isSalesman() ? 8 : 9;
        }
    },

    data: function() {
        return {
            sortIdAsc: true,
            sortPriceAsc: true,
            sortStatusAsc: true,
            sortTypeAsc: true,
            sortManAsc: true,
            sortCustomerAsc: true,

            sortManTypeAsc: true,
            sortManStatusAsc: true,
            sortManDateAsc: true 
        };
    },

    methods: {
        resetSort: function() {
            this.sortIdAsc = true;
            this.sortPriceAsc = true;
            this.sortStatusAsc = true;
            this.sortTypeAsc = true;
            this.sortManAsc = true;
            this.sortCustomerAsc = true;

            this.sortManTypeAsc = true;
            this.sortManStatusAsc = true;
            this.sortManDateAsc = true;
        },

        sortId: function(sortOrder) {
            this.resetSort();
            this.sortIdAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "id", sortOrder: sortOrder });
        },

        sortPrice: function(sortOrder) {
            this.resetSort();
            this.sortPriceAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "price", sortOrder: sortOrder });
        },

        sortStatus: function(sortOrder) {
            this.resetSort();
            this.sortStatusAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "status", sortOrder: sortOrder });
        },

        sortType: function(sortOrder) {
            this.resetSort();
            this.sortTypeAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "type", sortOrder: sortOrder });
        },

        sortMan: function(sortOrder) {
            this.resetSort();
            this.sortManAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "manifestation", sortOrder: sortOrder });
        },

        sortCustomer: function(sortOrder) {
            this.resetSort();
            this.sortCustomerAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "customer", sortOrder: sortOrder });
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
