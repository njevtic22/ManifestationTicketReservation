Vue.component("ticketsTable", {
    template: `
    <table border="5" class="table table-light table-striped table-hover table-borderless">
        <thead class="thead-dark">
            <tr>
                <th>
                    Ticket ID
                </th>
                <th>
                    Price
                </th>
                <th>
                    Status
                </th>
                <th>
                    Type
                </th>
                <th>
                    Manifestation
                </th>
                <th>
                    Customer
                </th>
                <th v-if="$root.isAdmin()">
                    Actions
                </th>
            </tr>
        </thead>
        <tbody v-if="tickets.length !== 0">
            <tr v-for="ticket in tickets">
                <td>{{ ticket.appId }}</td>
                <td>{{ ticket.price }} RSD</td>
                <td>{{ ticket.status }}</td>
                <td>{{ ticket.type }}</td>
                <td>{{ ticket.manifestation }}</td>
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
        tickets: Array
    },

    computed: {
        colspan() {
            return this.$root.isAdmin() ? 7 : 6;
        }
    },

    data: function() {
        return {};
    },

    methods: {
        
    },

    mounted() {},

    destroyed() {}
});
