Vue.component("allUsers", {
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
                        v-bind:currentDataSize="users.data.length"
                        v-bind:totalNumberOfResults="users.totalNumberOfResults"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    ></pageSizeSelect>

                    <pagination
                        v-bind:currentPage="page"
                        v-bind:hasPrevious="users.hasPreviousPage"
                        v-bind:hasNext="users.hasNextPage"

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
                    v-bind:users="users.data"
                    v-on:sort="performSort($event)"
                    v-on:deleteUser="confirmDeleteUser($event)"

                    ref="usersTable"
                ></usersTable>

                
                <br/>
                <div class="col d-flex justify-content-between">
                    <pageSizeSelect 
                        name="sizeInput"
                        v-bind:value="sizeStr"
                        v-bind:options="sizes"
                        v-bind:page="page"
                        v-bind:size="size"
                        v-bind:currentDataSize="users.data.length"
                        v-bind:totalNumberOfResults="users.totalNumberOfResults"
                        ref="pageSizeSelect"

                        v-on:select="changeSize($event)"
                    ></pageSizeSelect>

                    <pagination
                        v-bind:currentPage="page"
                        v-bind:hasPrevious="users.hasPreviousPage"
                        v-bind:hasNext="users.hasNextPage"

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

        <deleteUserModal
            id="deleteUserModalId"
            v-bind:userToDelete="userToDelete"
            v-on:deletedUserEvent="getAllUsers"
        ></deleteUserModal>

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


            // user: {
            //     id: 0,
            //     name: "",
            //     surname: "",
            //     username: "",
            //     dateOfBirth: "",
            //     gender: "",
            //     role: "",
            //     type: "",
            //     points: 0,
            // }

            users: {
                data: [],
                totalNumberOfResults: 0,
                hasNextPage: null,
                hasPrevious: null
            },

            userToDelete: {
                id: 0,
                name: "",
                surname: "",
                username: "",
                dateOfBirth: "",
                gender: "",
                role: "",
                type: "",
                points: 0,
            },

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
            this.page = 0;
            this.sizeStr = event;
            if (event === "All") {
                this.size = Infinity;
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
                this.sizeStr, 
                this.sortBy, 
                this.sortOrder,
                this.searchData,
                this.filterData,
                successCallback, 
                errorCallback
            );
        },

        confirmDeleteUser: function(user) {
            this.userToDelete = user;
            $("#deleteUserModalId").modal("show");
        }
    },

    mounted() {
        this.getAllUsers();
    },

    destroyed() {}
});
