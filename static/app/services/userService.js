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
        getAllUsers: function(page, size, successCallback, errorCallback) {
            const url = `${this.baseUrl}?page=${page}&size=${size}`
            axios
                .get(url)
                .then(response => { successCallback(response) })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
