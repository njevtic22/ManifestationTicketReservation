Vue.component("manifestationService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseUrl: "/api/manifestations"
        };
    },

    methods: {
        formFilterUrl: function(filterData) {
            let filterUrl = "";
            for (const key of Object.keys(filterData)) {
                if (filterData[key]) {
                    filterUrl += key + "=" + filterData[key] + "&";
                }
            }

            // remove trailing &
            if (filterUrl.length != 0) {
                filterUrl = filterUrl.slice(0, -1);
            }

            return filterUrl;
        },

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

        getActAndInactManifestations: function(page, size, sortBy, sortOrder, searchData, filterData, successCallback, errorCallback) {
            const searchUrl = this.formSearchUrl(searchData);
            const filterUrl = this.formFilterUrl(filterData);

            let url = `${this.baseUrl}?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}`;
            if (searchUrl.length != 0)
                url += `&${searchUrl}`;
            if (filterUrl.length != 0)
                url += `&${filterUrl}`;
                
            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

        getSalesmanManifestations: function(page, size, sortBy, sortOrder, searchData, filterData, successCallback, errorCallback) {
            const searchUrl = this.formSearchUrl(searchData);
            const filterUrl = this.formFilterUrl(filterData);

            let url = `${this.baseUrl}/forSalesman?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}`;
            if (searchUrl.length != 0)
                url += `&${searchUrl}`;
            if (filterUrl.length != 0)
                url += `&${filterUrl}`;
            
            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

        getManifestation: function(manifestationId, successCallback, errorCallback) {
            const url = `${this.baseUrl}/${manifestationId}`;
            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

        addManifestation: function(manifestationToAdd, successCallback, errorCallback) {
            axios
                .post(this.baseUrl, manifestationToAdd)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

        belognsToSalesman: function(manifestationId, successCallback, errorCallback) {
            const url = `${this.baseUrl}/${manifestationId}/belongsToSalesman`;
            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
