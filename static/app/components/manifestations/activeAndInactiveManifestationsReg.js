Vue.component("activeAndInactiveManifestationsReg", {
    template: `
    <div>
        <manifestationCards
            v-bind:manifestations="manifestations"
        >
        </manifestationCards>

        <manifestationService ref="manifestationService"></manifestationService>
    </div>
    `,

    data: function() {
        return {
            manifestations: [],

            page: 0,
            size: 5,
            sizeStr: "5",
            sizes: [
                "5",
                "10",
                "50",
                "All"
            ],
            
            sortBy: "date",
            sortOrder: "asc",
            
            searchData: {
                searchName: "",
                searchSurname: "",
                searchUsername: ""
            },
            filterData: {
                filterRole: "",
                filterType: ""
            }
        };
    },

    methods: {
        getActAndInactManifestations: function() {
            const successCallback = (response) => {
                this.manifestations = response.data;
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.getActAndInactManifestations(
                this.page,
                this.size,
                this.sortBy,
                this.sortOrder,
                this.searchData,
                this.filterData,
                successCallback,
                errorCallback
            );
        }
    },

    mounted() {
        this.getActAndInactManifestations();
    },

    destroyed() {}
});
