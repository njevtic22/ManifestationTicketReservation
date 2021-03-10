Vue.component("activeAndInactiveManifestationsReg", {
    template: `
    <div>
        <div class="form-row">
            <div class="form-group col-md-9">
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

            
            <div class="form-group col-md-3">
                <manifestationFilterSearchSortForm
                    v-on:submitSearchFilterSort="submitSearchFilterSort($event)"
                    v-on:resetSearchFilterSort="resetSearchFilterSort"
                ></manifestationFilterSearchSortForm>
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
                searchCity: "",
                searchStreet: "",

                searchDateFrom: "",
                searchDateTo: "",
                
                searchPriceFrom: 0,
                searchPriceTo: 0
            },
            filterData: {
                filterType: "",
                filterAvailable: ""
            },
        };
    },

    methods: {
        previousPage: function() {
            this.page--;
            this.getActAndInactManifestations();
        },
        
        nextPage: function() {
            this.page++;
            this.getActAndInactManifestations();
        },

        toPage: function(to) {
            this.page = to;
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

        submitSearchFilterSort: function(searchFilterSortEvent) {
            
            const myData = JSON.parse(JSON.stringify(searchFilterSortEvent));

            this.searchData = myData.searchData;
            this.filterData = myData.filterData;
            this.sortBy = myData.sortBy;
            this.sortOrder = myData.sortOrder;

        
            if (this.searchData.searchDateFrom)
                this.searchData.searchDateFrom += " 00:00:00";
            if (this.searchData.searchDateTo)
                this.searchData.searchDateTo += " 23:59:59";

            
            this.getActAndInactManifestations();
        },

        resetSearchFilterSort: function() {
            this.sortBy = "date";
            this.sortOrder = "asc";

            this.searchData = {
                searchName: "",
                searchCity: "",
                searchStreet: "",

                searchDateFrom: "",
                searchDateTo: "",
                
                searchPriceFrom: 0,
                searchPriceTo: 0
            },
            this.filterData = {
                filterType: "",
                filterAvailable: ""
            },


            this.getActAndInactManifestations();
        },

        getActAndInactManifestations: function() {
            this.manifestations = [];
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
