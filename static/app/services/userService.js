Vue.component("userService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseUsersUrl: "/api/users"
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

            let url = `${this.baseUsersUrl}?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}`;
            if (searchUrl.length != 0)
                url += `&${searchUrl}`;
            if (filterUrl.length != 0)
                url += `&${filterUrl}`;

            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
