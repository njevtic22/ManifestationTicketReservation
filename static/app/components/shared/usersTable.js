Vue.component("usersTable", {
    template: `
    <table border="5" class="table table-striped table-hover">
        <thead class="thead-dark">
            <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Username</th>
                <th>Date of birth</th>
                <th>Gender</th>
                <th>Role</th>
                <th>Type</th>
                <th>Points</th>
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
        return {};
    },

    methods: {

    },

    mounted() {},

    destroyed() {}
});
