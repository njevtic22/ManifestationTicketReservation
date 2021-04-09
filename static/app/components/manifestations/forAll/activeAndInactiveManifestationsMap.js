Vue.component("activeAndInactiveManifestationsMap", {
    template: `
    <div>
        <div class="form-row">
            <div class="form-group col-md-9">
                <div class="col d-flex justify-content-between">  
                    <pageSizeSelect
                        name="sizeInput"
                        v-bind:value="sizeStr"
                        v-bind:options="sizes"
                        v-bind:page="page"
                        v-bind:size="size"
                        v-bind:currentDataSize="manifestations.data.length"
                        v-bind:totalNumberOfResults="manifestations.totalNumberOfResults"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    >
                    </pageSizeSelect>

                    <pagination
                        v-bind:currentPage="page"
                        v-bind:hasPrevious="manifestations.hasPreviousPage"
                        v-bind:hasNext="manifestations.hasNextPage"

                        v-on:previous="previousPage"
                        v-on:next="nextPage"
                        v-on:to="toPage($event)"
                    >
                    </pagination>
                </div>

                <br/>
                <all-map
                    style="height: 800px; width: 100%;"
                    v-bind:manifestations="manifestations.data"
                    v-bind:zoom="5"
                >
                </all-map>
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
            loading: true,
            manifestations: {
                data: [],
                totalNumberOfResults: 0,
                hasNextPage: null,
                hasPreviousPage: null
            },

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
            this.page = 0;
            this.sizeStr = event;
            if (event === "All") {
                this.size = Infinity;
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
            this.loading = true;
            this.manifestations = {
                data: [],
                totalNumberOfResults: 0,
                hasNextPage: null,
                hasPreviousPage: null
            };
            
            const successCallback = (response) => {
                this.manifestations = response.data;
                this.loading = false;

                if (this.manifestations.data.length !== 0) {
                    const manifestation = response.data.data[0];
                    this.initCoords = [manifestation.location.latitude, manifestation.location.longitude];
                }
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.manifestationService.getActAndInactManifestations(
                this.page,
                this.sizeStr,
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
