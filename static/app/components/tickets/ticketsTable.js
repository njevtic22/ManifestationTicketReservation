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
            </tr>
        </thead>
        <tbody v-if="tickets.length !== 0">
            <tr v-for="ticket in tickets">
                <td>{{ ticket.appId }}</td>
                <td>{{ ticket.price }}</td>
                <td>{{ ticket.status }}</td>
                <td>{{ ticket.type }}</td>
                <td>{{ ticket.manifestation }}</td>
                <td>{{ ticket.customer ? ticket.customer : '/'}}</td>
            </tr>
        </tbody>

        <tbody v-else>
            <tr>
                <td colspan="9"><h4 class="text-center">No Results</h4></td>
            </tr>
        </tbody>
    </table>
    `,

    props: {
        tickets: Array
    },

    data: function() {
        return {};
    },

    methods: {
        
    },

    mounted() {},

    destroyed() {}
});
