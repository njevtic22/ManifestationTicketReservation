Vue.component("historiesTable", {
    template: `
    <table border="5" class="table table-light table-striped table-hover table-borderless">
        <thead class="thead-dark">
            <tr>
                <th>
                    Ticket ID
                </th>
                <th>
                    Withdrawal date
                </th>
                <th>
                    Ticket price
                </th>
                <th>
                    Ticket type
                </th>
                <th>
                    Manifestation
                </th>
                <th>
                    Manifestation status
                </th>
                <th>
                    Manifestation type
                </th>
                <th>
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
                <td>{{ history.manifestationStatus }}</td>
                <td>{{ history.manifestationType }}</td>
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
            /*
                private Long id;
    private String ticketId;

    private String withdrawalDate; // search --
    private double price;         // search --
    private String ticketType;   // filter --

    private String manifestation;           // search --
    private String manifestationStatus;    // filter --
    private String manifestationType;     // filter --
    private String manifestationDate;    // search
            
            */
        };
    },

    methods: {
    },

    mounted() {},

    destroyed() {}
});
