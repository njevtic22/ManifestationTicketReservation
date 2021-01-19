Vue.component("manifestationsTablePage", {
    template: `
    <baseLayout>
        <h1>manifestationsTablePage</h1>
    </baseLayout>
    `,

    data: function() {
        return {};
    },

    methods: {
        myFunction: function() {
            axios
                .method("/rest/", param)
                .then(response => {})
                .catch(err => {
                    this.$root.defaultCatchError(err);
                });
        }
    },

    mounted() {},

    destroyed() {}
});
