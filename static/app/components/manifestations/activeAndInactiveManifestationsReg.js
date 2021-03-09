Vue.component("activeAndInactiveManifestationsReg", {
    template: `
    <div>
        <div class="form-row">
            <div class="form-group col-md-10">
                <div class="col spaced">  
                    <pageSizeSelect
                        name="sizeInput"
                        v-bind:value="sizeStr"
                        v-bind:options="sizes"
                        v-bind:page="page"
                        v-bind:size="size"
                        v-bind:currentDataSize="manifestations.length"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    >
                    </pageSizeSelect>

                    <pagination
                        v-bind:currentPage="page"
                        v-bind:hasPrevious="page > 0"
                        v-bind:hasNext="manifestations.length != 0"

                        v-on:previous="previousPage"
                        v-on:next="nextPage"
                        v-on:to="toPage($event)"
                    >
                    </pagination>
                </div>


                <div class="d-flex justify-content-center" v-if="manifestations.length === 0">
                    <div class="spinner-grow text-secondary" role="status" style="width: 3rem; height: 3rem;">
                        <span class="sr-only">Loading...</span>
                    </div>
                </div>

                <manifestationCards v-else
                    v-bind:manifestations="manifestations"
                >
                </manifestationCards>
            </div>

            
            <div class="form-group col-md-2">
                <userSearchFilterForm
                ></userSearchFilterForm>
            </div>
        </div>


        <manifestationService ref="manifestationService"></manifestationService>
    </div>
    `,

    data: function() {
        return {
            manifestations: [],

            page: 0,
            size: 6,
            sizeStr: "6",
            sizes: [
                "6",
                "12",
                "72",
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
        previousPage: function() {
            this.page--;
            this.manifestations = [];
            this.getActAndInactManifestations();
        },
        
        nextPage: function() {
            this.page++;
            this.manifestations = [];
            this.getActAndInactManifestations();
        },

        toPage: function(to) {
            this.page = to;
            this.manifestations = [];
            this.getActAndInactManifestations();
        },

        changeSize: function(event) {
            this.sizeStr = event;
            if (event === "All") {
                this.size = 10000;
            } else {
                this.size = Number(event);
            }
            this.getActAndInactManifestations();
        },

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
