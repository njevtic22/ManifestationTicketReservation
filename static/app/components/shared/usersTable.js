Vue.component("usersTable", {
    template: `
    <table border="5" class="table table-striped table-hover">
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
            </tr>
        </thead>
        <tbody>
            <tr v-for="user in users">
                <td>{{ user.name }}</td>
                <td>{{ user.surname }}</td>
                <td>{{ user.username }}</td>
                <td>{{ user.dateOfBirth.substring(0, 11) }}</td>
                <td>{{ user.gender }}</td>
                <td>{{ user.role }}</td>
                <td>{{ user.type }}</td>
                <td>{{ user.points }}</td>
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
            console.log("nemanja");
            this.resetSort();
            this.sortNameAsc = sortOrder === "asc";
            this.$emit("sort", { sortBy: "name", sortOrder: sortOrder});
        },
        
        sortSurname: function(sortOrder) {
            this.resetSort();
            this.sortSurnameAsc = sortOrder === "asc";
        },
        
        sortUsername: function(sortOrder) {
            this.resetSort();
            this.sortUsernameAsc = sortOrder === "asc";
        },
        
        sortDate: function(sortOrder) {
            this.resetSort();
            this.sortDateAsc = sortOrder === "asc";
        },
        
        sortGender: function(sortOrder) {
            this.resetSort();
            this.sortGenderAsc = sortOrder === "asc";
        },
        
        sortRole: function(sortOrder) {
            this.resetSort();
            this.sortRoleAsc = sortOrder === "asc";
        },
        
        sortType: function(sortOrder) {
            this.resetSort();
            this.sortTypeAsc = sortOrder === "asc";
        },
        
        sortPoints: function(sortOrder) {
            this.resetSort();
            this.sortPointsAsc = sortOrder === "asc";
        }
    },

    mounted() {},

    destroyed() {}
});
