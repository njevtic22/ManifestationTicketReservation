Vue.component("base", {
    template:
    `
    <div>

    </div>
    `,

    data: function() {
        return {
            
        }
    },

    methods: {
        myFunction: function() {

            axios
            .method("/rest/", param)
            .then(response => {
                
            })
            .catch(err => {
                this.$root.defaultCatchError(err);
            });
        }
    },

    mounted() {

    },

    destroyed() {
        
    }
})