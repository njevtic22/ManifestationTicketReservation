Vue.component("allUsers", {
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
                        v-bind:currentDataSize="users.length"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    ></pageSizeSelect>

                    <pagination
                        v-bind:currentPage="page"
                        v-bind:hasPrevious="page > 0"
                        v-bind:hasNext="users.length != 0"

                        v-on:previous="previousPage"
                        v-on:next="nextPage"
                        v-on:to="toPage($event)"

                    ></pagination>

                    <button
                        type="button"
                        class="btn btn-primary"
                        data-toggle="modal"
                        data-target="#addSalesmanModalId"
                    >
                        Add salesman
                    </button>
                </div>

                <br/>

                <usersTable
                    v-bind:users="users"
                    v-on:sort="performSort($event)"
                    v-on:deleteUser="confirmDeleteUser($event)"

                    ref="usersTable"
                ></usersTable>
            </div>


            <div class="form-group col-md-3">
                <userSearchFilterForm
                    v-on:submitSearchFilter="submitSearchFilter($event)"
                    v-on:resetSearchFilter="resetSearchFilter"
                ></userSearchFilterForm>
            </div>
        </div>

        <addSalesmanModal 
            id="addSalesmanModalId"
            v-on:salesmanCreatedEvent="getAllUsers"
        ></addSalesmanModal>

        <userService ref="userService"></userService>
    </div>
    `,

    data: function() {
        return {
            page: 0,
            size: 5,
            sizeStr: "5",
            sizes: [
                "5",
                "10",
                "50",
                "All"
            ],

            users: [
                {
                    name: "",
                    surname: "",
                    username: "",
                    dateOfBirth: "",
                    gender: "",
                    role: "",
                    type: "",
                    points: "",
                }
            ],

            sortBy: "name",
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
            this.getAllUsers();
        },
        
        nextPage: function() {
            this.page++;
            this.getAllUsers();
        },

        toPage: function(to) {
            this.page = to;
            this.getAllUsers();
        },

        performSort: function(sortDetails) {
            this.sortBy = sortDetails.sortBy;
            this.sortOrder = sortDetails.sortOrder;
            this.getAllUsers();
        },

        changeSize: function(event) {
            this.sizeStr = event;
            if (event === "All") {
                this.size = 10000;
            } else {
                this.size = Number(event);
            }
            this.getAllUsers();
        },

        submitSearchFilter: function(searchFilterEvent) {
            this.searchData = searchFilterEvent.searchData;
            this.filterData = searchFilterEvent.filterData;
            this.getAllUsers();
        },

        resetSearchFilter: function() {
            this.searchData.searchName = "";
            this.searchData.searchSurname = "";
            this.searchData.searchUsername = "";

            this.filterData.filterRole = "";
            this.filterData.filterType = "";

            this.getAllUsers();
        },

        getAllUsers: function() {
            const successCallback = (response) => {
                this.users = response.data;
            };
            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.userService.getAllUsers(
                this.page, 
                this.size, 
                this.sortBy, 
                this.sortOrder,
                this.searchData,
                this.filterData,
                successCallback, 
                errorCallback
            );
        },

        confirmDeleteUser: function(user) {
            console.log(user);
        }
    },

    mounted() {
        this.getAllUsers();
    },

    destroyed() {}
});
