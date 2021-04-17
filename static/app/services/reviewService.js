Vue.component("reviewService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseUrl: "/api/reviews"
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

        getReviews: function(manifestationId, page, size, /* sortBy, sortOrder, searchData, */ filterData, successCallback, errorCallback) {
            const filterUrl = this.formSearchFilterUrl(filterData);

            let url = `${this.baseUrl}/${manifestationId}?page=${page}&size=${size}`;
            if (filterUrl.length != 0)
                url += `&${filterUrl}`;
                
            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

        addReview: function(manifestationId, reviewToAdd, successCallback, errorCallback) {
            const url = `${this.baseUrl}/${manifestationId}`;
            axios
                .post(url, reviewToAdd)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        },

        canLeaveReview: function(manifestationId, successCallback, errorCallback) {
            const url = `${this.baseUrl}/canLeaveReview/${manifestationId}`;
            axios
                .post(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
