Vue.component("userService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseUsersUrl: "/api/users",
            baseAdminsUrl: "/api/admins",
            baseSalesmenUrl: "/api/salesmen",
            baseCustomersUrl: "/api/customers"
        };
    },

    methods: {
        formSearchUrl: function(searchData) {
            let searchUrl = "";
            for (const key of Object.keys(searchData)) {
                if (searchData[key]) {
                    searchUrl += key + "=" + searchData[key] + "&";
                }
            }

            // Remove trailing &
            if (searchUrl.length != 0) {
                searchUrl = searchUrl.slice(0, -1);
            }

            return searchUrl;
        },

        formFilterUrl: function(filterData) {
            let filterUrl = "";
            for (const key of Object.keys(filterData)) {
                if (filterData[key]) {
                    filterUrl += key + "=" + filterData[key] + "&";
                }
            }

            // remove trailing &
            if (filterUrl.length != 0) {
                filterurl = filterUrl.slice(0, -1);
            }

            return filterUrl;
        },

        getAllUsers: function(page, size, sortBy, sortOrder, searchData, filterData, successCallback, errorCallback) {
            const searchUrl = this.formSearchUrl(searchData);
            const filterUrl = this.formFilterUrl(filterData);

            const url = `${this.baseUsersUrl}?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&${searchUrl}&${filterUrl}`;
            axios
                .get(url)
                .then(response => { successCallback(response) })
                .catch(error => { errorCallback(error); });
        },

        deleteSalesman: function(salesmanId, successCallback, errorCallback) {
            const url = `${this.baseSalesmenUrl}/${salesmanId}`;
            axios
                .delete(url)
                .then(response => { successCallback(response) })
                .catch(error => { errorCallback(error) });

                // {
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
        },

        deleteCustomer: function(customerId, successCallback, errorCallback) {
            const url = `${this.baseCustomersUrl}/${customerId}`;
            axios
                .delete(url)
                .then(response => { successCallback(response) })
                .catch(error => { errorCallback(error) });

                // {
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
