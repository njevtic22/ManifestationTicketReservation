Vue.component("customerService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseCustomersUrl: "/api/customers"
        };
    },

    methods: {
        // getCustomer: function(customerId, successCallback, errorCallback) {
        //     const url = `${this.baseCustomersUrl}/${customerId}`;
        //     axios
        //         .get(url)
        //         .then(response => { successCallback(response); })
        //         .catch(error => { errorCallback(error); });
        // },

        getType: function(successCallback, errorCallback) {
            const url = `${this.baseCustomersUrl}/type`;
            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error) });
        },

        updateCustomer: function(customerId, customerToUpdate, successCallback, errorCallback) {
            const url = `${this.baseCustomersUrl}/${customerId}`;
            axios
                .put(url, customerToUpdate)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error) });
        },

        deleteCustomer: function(customerId, successCallback, errorCallback) {
            const url = `${this.baseCustomersUrl}/${customerId}`;
            axios
                .delete(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });

                // url, {
                //     data: {
                //         id: self.chosenUser.id,
                //         name: self.chosenUser.name,
                //         surname: self.chosenUser.surname,
                //         email: self.chosenUser.email,
                //         password: self.chosenUser.password,
                //         organization: self.chosenUser.organization,
                //         role: self.chosenUser.role
                //     }
                // }
        }
    },

    mounted() {},

    destroyed() {}
});
