Vue.component("allUsers", {
    template: `
    <div>
        <pagination
            v-bind:currentPage="page"
            v-bind:hasPrevious="page > 0"
            v-bind:hasNext="users.length != 0"

            v-on:previous="previousPage"
            v-on:next="nextPage"
            v-on:to="toPage($event)"

            justifyContent="justify-content-center"
        ></pagination>
        <usersTable
            v-bind:users="users"
            v-on:sort="performSort($event)"
            ref="usersTable"
        >
        </usersTable>
        
        <userService ref="userService"></userService>
    </div>
    `,

    data: function() {
        return {
            page: 0,
            size: 5,

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
                successCallback, 
                errorCallback
            );
        }
    },

    mounted() {
        this.getAllUsers();
    },

    destroyed() {}
});
