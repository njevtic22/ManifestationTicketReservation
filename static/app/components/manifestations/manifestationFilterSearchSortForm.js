Vue.component("manifestationFilterSearchSortForm", {
    template: `
    <baseForm
        id="manifestationFSSForm"
        ref="manifestationFSSForm"
    >     
        <h4>Search and filter</h4>
        <textInput
            name="name"
            labelText="Name"
            class="form-group"
            v-model="searchData.searchName"
        >
        </textInput>
        <textInput
            name="city"
            labelText="City"
            class="form-group"
            v-model="searchData.searchCity"
        >
        </textInput>
        <textInput
            name="street"
            labelText="Street"
            class="form-group"
            v-model="searchData.searchStreet"
        >
        </textInput>

        <div class="row">
            <classicDateInput
                name="dateFrom"
                labelText="Date from"
                class="form-group col-md-6"
                v-model="searchData.searchDateFrom"
            >
            </classicDateInput>

            <classicDateInput
                name="dateTo"
                labelText="Date to"
                class="form-group col-md-6"
                v-model="searchData.searchDateTo"
            >
            </classicDateInput>
        </div>
        <div class="row">
            <numberInput
                name="priceFrom"
                labelText="Price from"
                class="form-group col-md-6"
                v-model="searchData.searchPriceFrom"
            >
            </numberInput>

            <numberInput
                name="priceTo"
                labelText="Price to"
                class="form-group col-md-6"
                v-model="searchData.searchPriceTo"
            >
            </numberInput>
        </div>

        <!-- <h4>Filter by</h4> -->
        <div class="row">
            <selectInput
                name="manType"
                labelText="Type"
                v-bind:value="typeValue"
                v-bind:options="typeOptions"
                class="form-group col-md-6"
                required
                
                v-on:select="changeType($event)"
            ></selectInput>

            <selectInput
                name="manAvailable"
                labelText="Available (not sold)"
                v-bind:value="availableValue"
                v-bind:options="availableOptions"
                class="form-group col-md-6"
                required
                
                v-on:select="changeAvailable($event)"
            ></selectInput>
        </div>

        <h4>Sort</h4>
        <div class="row">
            <selectInput
                name="sortBy"
                labelText="Sort by"
                v-bind:value="sortBy"
                v-bind:options="sortByOptions"
                class="form-group col-md-6"
                required
                
                v-on:select="changeSortBy($event)"
            ></selectInput>

            <selectInput
                name="sortOrder"
                labelText="Order"
                v-bind:value="sortOrder"
                v-bind:options="sortOrderOptions"
                class="form-group col-md-6"
                required
                
                v-on:select="changeSortOrder($event)"
            ></selectInput>
        </div>

        <div class="text-right">
            <button
                type="button"
                class="btn btn-success"
                v-on:click="submitSearchFilterSort"
            >
                Apply
            </button>
            
            <button
                type="button"
                class="btn btn-danger"
                v-on:click="resetSearchFilterSort"
            >
                Reset
            </button>
        </div>
    </baseForm>
    `,

    data: function() {
        return {
            searchData: {
                searchName: "",
                searchCity: "",
                searchStreet: "",

                searchDateFrom: "",
                searchDateTo: "",
                
                searchPriceFrom: "",
                searchPriceTo: ""
            },
            filterData: {
                filterType: "",
                filterAvailable: ""
            },


            typeValue: "",
            typeOptions: [
                "",
                "CONCERT",
                "FESTIVAL",
                "THEATER"
            ],

            availableValue: "",
            availableOptions: [
                "",
                "AVAILABLE"
            ],



            sortBy: "Date",
            sortByOptions: [
                "Date",
                "Name",
                "Price",
                "Location"
            ],
            sortOrder: "Ascending",
            sortOrderOptions: [
                "Ascending",
                "Descending"
            ]
        };
    },

    methods: {
        changeType: function(newType) {
            this.typeValue = newType;
            this.filterData.filterType = newType;
        },

        changeAvailable: function(newAvailable) {
            this.availableValue = newAvailable;
            this.filterData.filterAvailable = newAvailable;
        },

        changeSortBy: function(newSortBy) {
            this.sortBy = newSortBy;
        },

        changeSortOrder: function(newSortOrder) {
            this.sortOrder = newSortOrder;
        },

        resetSearchFilterSort: function() {
            this.sortBy = "Date";
            this.sortOrder = "Ascending";

            this.typeValue = "";
            this.availableValue = "";

            this.searchData.searchName = "";
            this.searchData.searchCity = "";
            this.searchData.searchStreet = "";
            this.searchData.searchDateFrom = "";
            this.searchData.searchDateTo = "";
            this.searchData.searchPriceFrom = "";
            this.searchData.searchPriceTo = "";

            this.filterData.filterType = "";
            this.filterData.filterAvailable = "";

            this.$emit('resetSearchFilterSort');
        },

        submitSearchFilterSort: function() {

            let sortOrderEmit = "";
            if (this.sortOrder === "Ascending")
                sortOrderEmit = "asc";
            else
                sortOrderEmit = "desc";

            this.$emit('submitSearchFilterSort', {
                searchData: this.searchData,
                filterData: this.filterData,
                sortBy: this.sortBy,
                sortOrder: sortOrderEmit,
            })
        }
    },

    mounted() {},

    destroyed() {}
});
