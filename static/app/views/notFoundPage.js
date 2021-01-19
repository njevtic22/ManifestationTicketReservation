Vue.component("notFoundPage", {
    template:
    `
    <baseLayout>
        <div class="error-center">
            <img 
            src="/images/404 error.png" 
            alt="Even image is not found" 
            width="280" height="210">
            <h1 class="text-danger">Page not found</h1>
        </div>
    </baseLayout>
    `,

    data: function() {
        return {
        }
    },

    methods: {

    },

    mounted() {
    }
})