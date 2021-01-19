Vue.component("unauthorizedPage", {
    template:
    `
    <baseLayout>
        <div class="error-center">
            <img 
            src="/images/401 error.png" 
            alt="Even image is unauthorized" 
            width="280" height="210">
            <h1 class="text-danger">Unauthorized acces</h1>            
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