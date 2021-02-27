Vue.component("userService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseUrl: "/api/users"
        };
    },

    methods: {
        getAllUsers: function(page, size, sortBy, sortOrder, successCallback, errorCallback) {
            const url = `${this.baseUrl}?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}`;
            axios
                .get(url)
                .then(response => { successCallback(response) })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
