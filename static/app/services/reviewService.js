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
        getReviews: function(manifestationId, page, size, successCallback, errorCallback) {
            const url = `${this.baseUrl}/${manifestationId}?page=${page}&size=${size}`;
            axios
                .get(url)
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
