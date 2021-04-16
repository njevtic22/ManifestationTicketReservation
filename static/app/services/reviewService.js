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
        canLeaveReview: function(manifestationId, successCallback, errorCallback) {
            const url = `${this.baseUrl}/canLeaveReview/${manifestationId}`;
            axios
                .get(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
