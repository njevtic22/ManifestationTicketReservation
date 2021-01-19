Vue.component("logOutButton", {
    template: `
    <button
        class="btn btn-dark btn-block"
        v-on:click="logOut"
        type="button"
    >
        Log out
    </button>
    `,

    data: function() {
        return {};
    },

    methods: {
        logOut: function() {
            this.$root.clearStorageAndHeader();
            this.$router.push({
                name: "LogInPage"
            });
        }
    },

    mounted() {}
});
