Vue.component("usersTable", {
    template: `
    <table border="5" class="table table-light table-striped table-hover table-borderless">
        <thead class="thead-dark">
            <tr>
                <th>
                    <caret-up-square-fill-icon v-if="sortNameAsc" v-on:click="sortName('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortName('asc')"></caret-down-square-fill-icon>
                    Name
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortSurnameAsc" v-on:click="sortSurname('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortSurname('asc')"></caret-down-square-fill-icon>
                    Surname
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortUsernameAsc" v-on:click="sortUsername('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortUsername('asc')"></caret-down-square-fill-icon>
                    Username
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortDateAsc" v-on:click="sortDate('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortDate('asc')"></caret-down-square-fill-icon>
                    Date of birth
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortGenderAsc" v-on:click="sortGender('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortGender('asc')"></caret-down-square-fill-icon>
                    Gender
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortRoleAsc" v-on:click="sortRole('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortRole('asc')"></caret-down-square-fill-icon>
                    Role
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortTypeAsc" v-on:click="sortType('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortType('asc')"></caret-down-square-fill-icon>
                    Type
                </th>
                <th>
                    <caret-up-square-fill-icon v-if="sortPointsAsc" v-on:click="sortPoints('desc')"></caret-up-square-fill-icon>
                    <caret-down-square-fill-icon v-else v-on:click="sortPoints('asc')"></caret-down-square-fill-icon>
                    Points
                </th>
                <th v-if="$root.isAdmin()">
                    Actions
                </th>
            </tr>
        </thead>
        <tbody v-if="users.length !== 0">
            <tr v-for="user in users">
                <td>{{ user.name }}</td>
                <td>{{ user.surname }}</td>
                <td>{{ user.username }}</td>
                <td>{{ user.dateOfBirth.substring(0, 11) }}</td>
                <td>{{ user.gender }}</td>
                <td>{{ user.role }}</td>
                <td>{{ user.type }}</td>
                <td>{{ user.points }}</td>
                <td class="text-center" v-if="$root.isAdmin()">
                    <button 
                        type="button" 
                        class="btn btn-link btn-sm"
                        v-if="user.role !== 'ADMIN'"
                    
                        v-on:click="$emit('deleteUser', user)"
                    >
                        <trash-fill-icon></trash-fill-icon>
                    </button>
                </td>
            </tr>
        </tbody>

        <tbody v-else>
            <tr>
                <td colspan="9"><h4 class="text-center">No Results</h4></td>
            </tr>
        </tbody>
    </table>
    `,

    props: {
        users: Array
    },

    data: function() {
        return {
            sortNameAsc: true,
            sortSurnameAsc: true,
            sortUsernameAsc: true,
            sortDateAsc: true,
            sortGenderAsc: true,
            sortRoleAsc: true,
            sortTypeAsc: true,
            sortPointsAsc: true
        };
    },

    methods: {
        resetSort: function() {
            this.sortNameAsc = true,
            this.sortSurnameAsc = true,
            this.sortUsernameAsc = true,
            this.sortDateAsc = true,
            this.sortGenderAsc = true,
            this.sortRoleAsc = true,
            this.sortTypeAsc = true,
            this.sortPointsAsc = true
        },

        sortName: function(sortOrder) {
            this.resetSort();
            this.sortNameAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "name", sortOrder: sortOrder});
        },
        
        sortSurname: function(sortOrder) {
            this.resetSort();
            this.sortSurnameAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "surname", sortOrder: sortOrder});
        },
        
        sortUsername: function(sortOrder) {
            this.resetSort();
            this.sortUsernameAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "username", sortOrder: sortOrder});
        },
        
        sortDate: function(sortOrder) {
            this.resetSort();
            this.sortDateAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "date", sortOrder: sortOrder});
        },
        
        sortGender: function(sortOrder) {
            this.resetSort();
            this.sortGenderAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "gender", sortOrder: sortOrder});
        },
        
        sortRole: function(sortOrder) {
            this.resetSort();
            this.sortRoleAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "role", sortOrder: sortOrder});
        },
        
        sortType: function(sortOrder) {
            this.resetSort();
            this.sortTypeAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "type", sortOrder: sortOrder});
        },
        
        sortPoints: function(sortOrder) {
            this.resetSort();
            this.sortPointsAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "points", sortOrder: sortOrder});
        }
    },

    mounted() {},

    destroyed() {}
});
