Vue.component("ticketSearchFilterForm", {
    template: `
    <baseForm
        id="ticketSearchFilterForm"
        ref="ticketSearchFilterForm"
    >
        <h4>Search and filter</h4>
        <div class="row">
            <textInput
                name="manName"
                labelText="Manifestation name"
                class="form-group col-md-12"
                v-model="searchData.searchManifestation"
            >
            </textInput>
        </div>
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
        <div class="row">
            <selectInput
                name="ticketType"
                labelText="Ticket type"
                v-bind:value="typeValue"
                v-bind:options="typeOptions"
                class="form-group"
                v-bind:class="[colClass]"
                required
                
                v-on:select="changeType($event)"
            ></selectInput>

            <selectInput
                name="ticketStatus"
                labelText="Ticket status"
                v-bind:value="statusValue"
                v-bind:options="statusOptions"
                class="form-group col-md-6"
                required
                
                v-if="!$root.isCustomer()"
                
                v-on:select="changeTicketStatus($event)"
            ></selectInput>

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
                searchManifestation: "",

                searchPriceFrom: "",
                searchPriceTo: "",

                searchDateFrom: "",
                searchDateTo: ""
            },
            filterData: {
                filterType: "",
                filterTicketStatus: "",
                filterManifestationType: "",
                filterManifestationStatus: ""
            },


            typeValue: "",
            typeOptions: [
                "",
                "REGULAR",
                "FAN_PIT",
                "VIP"
            ],

            statusValue: "",
            statusOptions: [
                "",
                "FREE",
                "RESERVED"
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
            ],
        };
    },

    computed: {
        colClass() {
            return this.$root.isCustomer() ? "col-md-12" : "col-md-6";
        }
    },

    methods: {
        changeType: function(newTicketType) {
            this.typeValue = newTicketType;
            this.filterData.filterType = newTicketType;
        },

        changeTicketStatus: function(newTicketStatus) {
            this.statusValue = newTicketStatus;
            this.filterData.filterTicketStatus = newTicketStatus;
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
                searchManifestation: "",

                searchPriceFrom: "",
                searchPriceTo: "",

                searchDateFrom: "",
                searchDateTo: ""
            };
            this.filterData = {
                filterType: "",
                filterTicketStatus: "",
                filterManifestationType: "",
                filterManifestationStatus: ""
            };


            this.typeValue = "";
            this.statusValue = "";
            this.manTypeValue = "";
            this.manStatusValue = "";
            
            this.$emit('resetSearchFilter');
        },

        submitSearchFilter: function() {
            this.$emit('submitSearchFilter', {
                searchData: this.searchData,
                filterData: this.filterData
            })
        }
    },

    mounted() {},

    destroyed() {}
});
