Vue.component("base", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {};
    },

    methods: {
        myFunction: function(successCallback, errorCallback) {
            const url = "";
            axios
                .method(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });
        }
    },

    mounted() {},

    destroyed() {}
});
