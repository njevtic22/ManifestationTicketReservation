Vue.component("historySearchFilterForm", {
    template: `
    <baseForm
        id="historySearchFilterForm"
        ref="historySearchFilterForm"
    >
        <h4>Search and filter</h4>
        <div class="row">
            <textInput
                name="manName"
                labelText="Manifestation name"
                class="form-group col-md-12"
                v-model="searchData.searchManifestationName"
            >
            </textInput>
        </div>
        <div class="row">
            <classicDateInput
                name="dateFrom"
                labelText="Manifesattion date from"
                class="form-group col-md-6"
                v-model="searchData.searchManifestationDateFrom"
            >
            </classicDateInput>

            <classicDateInput
                name="dateTo"
                labelText="Manifesattion date to"
                class="form-group col-md-6"
                v-model="searchData.searchManifestationDateTo"
            >
            </classicDateInput>
        </div>
        <div class="row">
            <classicDateInput
                name="dateFrom"
                labelText="Withdrawal date from"
                class="form-group col-md-6"
                v-model="searchData.searchHistoryDateFrom"
            >
            </classicDateInput>

            <classicDateInput
                name="dateTo"
                labelText="Withdrawal date to"
                class="form-group col-md-6"
                v-model="searchData.searchHistoryDateTo"
            >
            </classicDateInput>
        </div>
        <div class="row">
            <numberInput
                name="priceFrom"
                labelText="Price from"
                class="form-group col-md-6"
                v-model="searchData.searchTicketPriceFrom"
            >
            </numberInput>

            <numberInput
                name="priceTo"
                labelText="Price to"
                class="form-group col-md-6"
                v-model="searchData.searchTicketPriceTo"
            >
            </numberInput>
        </div>
        <div class="row">
            <selectInput
                name="ticketType"
                labelText="Ticket type"
                v-bind:value="ticketTypeValue"
                v-bind:options="ticketTypeOptions"
                class="form-group col-md-12"
                required
                
                v-on:select="changeTicketType($event)"
            ></selectInput>
        </div>
        <div class="row">        
            <selectInput
                name="manType"
                labelText="Manfiestation type"
                v-bind:value="manTypeValue"
                v-bind:options="manTypeOptions"
                class="form-group col-md-6"
                required
                
                v-on:select="changeManType($event)"
            ></selectInput>
            
            <selectInput
                name="manStatus"
                labelText="Manfiestation status"
                v-bind:value="manStatusValue"
                v-bind:options="manStatusOptions"
                class="form-group col-md-6"
                required
                
                v-on:select="changeManStatus($event)"
            ></selectInput>
        </div>

        <div class="text-right">
            <button
                type="button"
                class="btn btn-success"
                v-on:click="submitSearchFilter"
            >
                Apply
            </button>
            
            <button
                type="button"
                class="btn btn-danger"
                v-on:click="resetSearchFilter"
            >
                Reset
            </button>
        </div>
    </baseForm>
    `,

    data: function() {
        return {
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



            ticketTypeValue: "",
            ticketTypeOptions: [
                "",
                "REGULAR",
                "FAN_PIT",
                "VIP"
            ],

            manTypeValue: "",
            manTypeOptions: [
                "",
                "CONCERT",
                "FESTIVAL",
                "THEATER"
            ],

            manStatusValue: "",
            manStatusOptions: [
                "",
                "CREATED",
                "REJECTED",
                "ACTIVE",
                "INACTIVE"
            ]
        };
    },

    methods: {
        changeTicketType: function(newTicketType) {
            this.ticketTypeValue = newTicketType;
            this.filterData.filterTicketType = newTicketType;
        },

        changeManType: function(newManType) {
            this.manTypeValue = newManType;
            this.filterData.filterManifestationType = newManType;
        },

        changeManStatus: function(newManStatus) {
            this.manStatusValue = newManStatus;
            this.filterData.filterManifestationStatus = newManStatus;
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

            
            this.ticketTypeValue = "";
            this.manTypeValue = "";
            this.manStatusValue = "";
            
            this.$emit('resetSearchFilter');
        },

        submitSearchFilter: function() {
            this.$emit('submitSearchFilter', {
                searchData: this.searchData,
                filterData: this.filterData
            });
        }
    },

    mounted() {},

    destroyed() {}
});
