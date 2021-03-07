Vue.component("manifestationsTable", {
    template: `
    <div>
        <h1>Manifestations table</h1>

        <div>
            <div v-for="manifestation in manifestations">
sadfsd
                {{JSON.stringify(manifestation)}}
            </div>
        </div>
    </div>
    `,

    data: function() {
        return {
            manifestations: []
        };
    },

    methods: {
        getManifestations: function() {

            // TODO: Implement this in separate service with pagination and sortering and filtering
            axios
                .get("/api/manifestations")
                .then(response => {
                    this.manifestations = response.data;
                })
                .catch(error => {
                    console.log(error.data);
                });
        }
    },

    mounted() {
        this.getManifestations();
    },

    destroyed() {}
});
