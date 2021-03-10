Vue.component("userSearchFilterForm", {
    template: `
    <baseForm
        id="userSearchFilterForm"
        ref="userSearchFilterForm"
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
            name="surname"
            labelText="Surname"
            class="form-group"
            v-model="searchData.searchSurname"
        >
        </textInput>
        <textInput
            name="username"
            labelText="Username"
            class="form-group"
            v-model="searchData.searchUsername"
        >
        </textInput>


        <!-- <h4>Filter by</h4> -->
        <div class="row">
            <selectInput
                placeholder=""
                name="userRole"
                labelText="Role"
                v-bind:value="roleValue"
                v-bind:options="roleOptions"
                class="form-group col-md-6"
                required
                
                v-on:select="changeRole($event)"
            ></selectInput>

            <selectInput
                placeholder=""
                name="userType"
                labelText="Type"
                v-bind:value="typeValue"
                v-bind:options="typeOptions"
                class="form-group col-md-6"
                required
                
                v-on:select="changeType($event)"
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
                searchName: "",
                searchSurname: "",
                searchUsername: ""
            },
            filterData: {
                filterRole: "",
                filterType: ""
            },


            roleValue: "",
            roleOptions: [
                "",
                "ADMIN",
                "SALESMAN",
                "CUSTOMER"
            ],

            typeValue: "",
            typeOptions: [
                "",
                "GOLD",
                "SILVER",
                "BRONZE"
            ]
        };
    },

    methods: {
        changeRole: function(newRole) {
            this.roleValue = newRole;
            this.filterData.filterRole = newRole;
        },

        changeType: function(newType) {
            this.typeValue = newType;
            this.filterData.filterType = newType;
        },

        resetSearchFilter: function() {
            this.roleValue = "";
            this.typeValue = "";
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
