Vue.component("forbiddenPage", {
    template:
    `
    <baseLayout>
        <div class="error-center">
            <img 
            src="/images/403 error.png" 
            alt="Even image is forbidden" 
            width="280" height="210">
            <h1 class="text-danger">Forbidden acces</h1>            
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
});