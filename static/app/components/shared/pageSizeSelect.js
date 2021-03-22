Vue.component("pageSizeSelect", {
    template: `
    <div style="display: inline-flex">
        <span v-if="currentDataSize === 0" style="margin-top: 7px">
            Showing 0 results,
        </span>
        <span v-else-if="value.toLowerCase() === 'all'" style="margin-top: 7px">
            Showing {{ totalNumberOfResults }} results,
        </span>
        <span v-else style="margin-top: 7px">
            Showing {{ page * size + 1 }} to {{ page * size + currentDataSize }} of {{ totalNumberOfResults }} results,
        </span>
        &nbsp;

        <selectInput
            :name="name"
            :value="value"
            :options="options"
            class="mb-2 mr-sm-2"
            required
            ref="selectInput"

            v-on:select="$emit('select', $event)"
        ></selectInput>

        <span style="margin-top: 7px">results per page</span>
    </div>
    `,

    props: {
        name: String,
        value: String,
        options: Array,
        page: Number,
        size: Number,
        currentDataSize: Number,
        totalNumberOfResults: Number
    },

    data: function() {
        return {};
    },

    methods: {},

    mounted() {},

    destroyed() {}
});
