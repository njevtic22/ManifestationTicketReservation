Vue.component("histories", {
    template: `
    <div>
        <div class="row">
            <div class="form-group col-md-9">
                <div class="col d-flex justify-content-between">
                    <pageSizeSelect
                        name="sizeInput"
                        v-bind:value="sizeStr"
                        v-bind:options="sizes"
                        v-bind:page="page"
                        v-bind:size="size"
                        v-bind:currentDataSize="histories.data.length"
                        v-bind:totalNumberOfResults="histories.totalNumberOfResults"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    >
                    </pageSizeSelect>

                    <pagination
                        v-bind:currentPage="page"
                        v-bind:hasPrevious="histories.hasPreviousPage"
                        v-bind:hasNext="histories.hasNextPage"

                        v-on:previous="previousPage"
                        v-on:next="nextPage"
                        v-on:to="toPage($event)"
                    >
                    </pagination>
                </div>
                <br/>

                <historiesTable
                    v-bind:loading="loading"
                    v-bind:histories="histories.data"

                    v-on:sort="performSort($event)"
                >
                </historiesTable>

                
                
                <br/>
                <div class="col d-flex justify-content-between">
                    <pageSizeSelect
                        name="sizeInput"
                        v-bind:value="sizeStr"
                        v-bind:options="sizes"
                        v-bind:page="page"
                        v-bind:size="size"
                        v-bind:currentDataSize="histories.data.length"
                        v-bind:totalNumberOfResults="histories.totalNumberOfResults"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    >
                    </pageSizeSelect>

                    <pagination
                        v-bind:currentPage="page"
                        v-bind:hasPrevious="histories.hasPreviousPage"
                        v-bind:hasNext="histories.hasNextPage"

                        v-on:previous="previousPage"
                        v-on:next="nextPage"
                        v-on:to="toPage($event)"
                    >
                    </pagination>
                </div>
            </div>

            
            <div class="form-group col-md-3">
                <historySearchFilterForm
                    v-on:submitSearchFilter="submitSearchFilter($event)"
                    v-on:resetSearchFilter="resetSearchFilter"
                ></historySearchFilterForm>
            </div>
        </div>
        <historyService ref="historyService"></historyService>
    </div>
    `,

    data: function() {
        return {
            loading: true,
            histories: {
                data: [],
                totalNumberOfResults: 0,
                hasPreviousPage: null,
                hasNextPage: null
            },
            

            page: 0,
            size: 10,
            sizeStr: "5",
            sizes: [
                "5",
                "10",
                "50",
                "All"
            ],

            
            sortBy: "historydate",
            sortOrder: "asc",
            
            
            searchData: {
                searchHistoryDateFrom: "",
                searchHistoryDateTo: "",

                searchTicketPriceFrom: "",
                searchTicketPriceTo: "",

                searchManifestationName: "",

                searchManifestationDateFrom: "",
                searchManifestationDateTo: "",
            },
            filterData: {
                filterTicketType: "",
                filterManifestationStatus: "",
                filterManifestationType: ""
            },
        };
    },

    methods: {
        previousPage: function() {
            this.page--;
            this.getHistories();
        },
        
        nextPage: function() {
            this.page++;
            this.getHistories();
        },

        toPage: function(to) {
            this.page = to;
            this.getHistories();
        },

        changeSize: function(event) {
            this.page = 0;
            this.sizeStr = event;
            if (event === "All") {
                this.size = Infinity;
            } else {
                this.size = Number(event);
            }
            this.getHistories();
        },

        performSort: function(sortDetails) {
            this.sortBy = sortDetails.sortBy;
            this.sortOrder = sortDetails.sortOrder;
            this.getHistories();
        },

        submitSearchFilter: function(searchFilterEvent) {
            const myData = JSON.parse(JSON.stringify(searchFilterEvent));

            this.searchData = myData.searchData;
            this.filterData = myData.filterData;

            if (this.searchData.searchHistoryDateFrom)
                this.searchData.searchHistoryDateFrom += " 00:00:00";
            if (this.searchData.searchHistoryDateTo)
                this.searchData.searchHistoryDateTo += " 23:59:59";
                
            if (this.searchData.searchManifestationDateFrom)
                this.searchData.searchManifestationDateFrom += " 00:00:00";
            if (this.searchData.searchManifestationDateTo)
                this.searchData.searchManifestationDateTo += " 23:59:59";
            
            this.getHistories();
        },

        resetSearchFilter: function() {
            this.searchData = {
                searchHistoryDateFrom: "",
                searchHistoryDateTo: "",

                searchTicketPriceFrom: "",
                searchTicketPriceTo: "",

                searchManifestationName: "",

                searchManifestationDateFrom: "",
                searchManifestationDateTo: "",
            };
            this.filterData = {
                filterTicketType: "",
                filterManifestationStatus: "",
                filterManifestationType: ""
            };

            this.getHistories();
        },

        getHistories: function() {
            this.loading = true;
            this.tickets = {
                data: [],
                totalNumberOfResults: 0,
                hasNextPage: null,
                hasPreviousPage: null
            };

            const successCallback = (response) => {
                this.histories = response.data;
                this.loading = false;
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.historyService.getHistories(
                this.page,
                this.sizeStr,
                this.sortBy,
                this.sortOrder,
                this.searchData,
                this.filterData,
                successCallback,
                errorCallback
            )
        }
    },

    mounted() {
        this.getHistories();
    },

    destroyed() {}
});
