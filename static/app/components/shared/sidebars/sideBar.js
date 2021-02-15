Vue.component("sideBar", {
    template: `
    <div>
        <adminSidebar     v-if="$root.isAdmin()"></adminSidebar>
        <salesmanSidebar  v-else-if="$root.isSalesman()"></salesmanSidebar>
        <customerSidebar  v-else-if="$root.isCustomer()"></customerSidebar>
        <anonymousSidebar v-else="!$root.isUserLoggedIn()"></anonymousSidebar>
    </div>
    `,

    data: function() {
        return {};
    },

    methods: {},

    mounted() {
    }
});
