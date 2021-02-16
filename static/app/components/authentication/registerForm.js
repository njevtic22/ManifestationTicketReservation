Vue.component("registerForm", {
    template: `
    <baseForm
        id="registerForm" 
        class="text-center"
        ref="registerForm"
    >
        <!-- class="login-center"  or register-center-->
        <h1>Register form</h1>
    </baseForm>
    `,

    data: function() {
        return {};
    },

    methods: {
        myFunction: function() {
            axios
                .method("/api/", param)
                .then(response => {})
                .catch(err => {
                    this.$root.defaultCatchError(err);
                });
        }
    },

    mounted() {},

    destroyed() {}
});
