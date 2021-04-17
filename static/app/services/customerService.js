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
        formSearchFilterUrl: function(searchFilterData) {
            let url = "";
            for (const key of Object.keys(searchFilterData)) {
                if (searchFilterData[key]) {
                    url += key + "=" + searchFilterData[key] + "&";
                }
            }

            // Remove trailing &
            if (url.length != 0) {
                url = url.slice(0, -1);
            }
            return url;
        },

        getSuspiciousCustomers: function(page, size, sortBy, sortOrder, searchData, filterData, successCallback, errorCallback) {
            const searchUrl = this.formSearchFilterUrl(searchData);
            const filterUrl = this.formSearchFilterUrl(filterData);

            let url = `${this.baseCustomersUrl}/suspicious?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}`;
            if (searchUrl.length != 0)
                url += `&${searchUrl}`;
            if (filterUrl.length != 0)
                url += `&${filterUrl}`;

            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

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
