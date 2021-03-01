Vue.component("pageSizeSelect", {
    template: `
    <div style="display: inline-flex">
        <span v-if="currentDataSize === 0" style="margin-top: 7px">
            Showing 0 rows,
        </span>
        <span v-else style="margin-top: 7px">
            Showing {{ page * size + 1 }} to {{ page * size + currentDataSize }} rows,
        </span>
        &nbsp;

        <selectInput
            componentClass="mb-2 mr-sm-2"
            :name="name"
            :value="value"
            :options="options"
            required
            ref="selectInput"

            v-on:select="$emit('select', $event)"
        ></selectInput>

        <span style="margin-top: 7px">rows per page</span>
    </div>
    `,

    props: {
        name: String,
        value: String,
        options: Array,
        page: Number,
        size: Number,
        currentDataSize: Number
    },

    data: function() {
        return {};
    },

    methods: {},

    mounted() {},

    destroyed() {}
});
