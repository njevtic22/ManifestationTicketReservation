Vue.component("authenticationService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseUrl: "/api/authentication"
        };
    },

    methods: {
        login: function(userToLogin, successCallback, errorCallback) {
            const url = `${this.baseUrl}/login`;
            axios
                .post(url, userToLogin)
                .then(response => {
                    successCallback(response);
                })
                .catch(error => {
                    errorCallback(error);
                });
        },

        registerCustomer: function(customerToRegister, successCallback, errorCallback) {
            const url = `${this.baseUrl}/registerCustomer`;
            axios
                .post(url, customerToRegister)
                .then(response => {
                    successCallback(response);
                })
                .catch(error => {
                    errorCallback(error);
                });
        },

        registerSalesman: function(salesmanToRegister, successCallback, errorCallback) {
            const url = `${this.baseUrl}/registerSalesman`;
            axios
                .post(url, salesmanToRegister)
                .then(response => {
                    successCallback(response);
                })
                .catch(error => {
                    errorCallback(error);
                });
        },

        getAuthenticated: function(successCallback, errorCallback) {
            const url = `${this.baseUrl}/authenticated`;
            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error) });
        },

        changePassword: function(userId, passwordData, successCallback, errorCallback) {
            const url = `${this.baseUrl}/${userId}/password`;
            axios
                .post(url, passwordData)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
