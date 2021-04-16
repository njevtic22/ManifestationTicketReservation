Vue.component("historyService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseUrl: "/api/histories"
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
        
        getHistories: function(page, size, sortBy, sortOrder, searchData, filterData, successCallback, errorCallback) {
            const filterUrl = this.formSearchFilterUrl(filterData);
            const searchUrl = this.formSearchFilterUrl(searchData);
            
            let url = `${this.baseUrl}?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}`;
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
