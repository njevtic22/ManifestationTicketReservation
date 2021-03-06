Vue.component("adminService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseAdminsUrl: "/api/admins"
        };
    },

    methods: {
        // getAdmin: function(adminId, successCallback, errorCallback) {
        //     const url = `${this.baseAdminsUrl}/${adminId}`;
        //     axios
        //         .get(url)
        //         .then(response => { successCallback(response); })
        //         .catch(error => { errorCallback(error); });
        // },

        updateAdmin: function(adminId, adminToUpdate, successCallback, errorCallback) {
            const url = `${this.baseAdminsUrl}/${adminId}`;
            axios
                .put(url, adminToUpdate)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error) });
        },
    },

    mounted() {},

    destroyed() {}
});
