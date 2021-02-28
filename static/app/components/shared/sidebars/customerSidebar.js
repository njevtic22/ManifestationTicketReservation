Vue.component("customerSidebar", {
    template: `
    <nav class="bg-light list-group list-group-flush sidebar"> 
        <div class="sidebar-sticky">
            <h5 class="sidebar-heading"><a href="#/" id="mainAnchor">Manifestation service</a></h5>
            <ul class="nav flex-column">
    
                <li class="nav-item">
                    <router-link class="nav-link" to="/customer/profile">
                        <person-circle-icon class="sidebar-icon"></person-circle-icon>
                        Profile
                    </router-link>
                </li>
                
                <li class="nav-item">
                    <router-link class="nav-link" to="/customer/manifestations/table">
                        <table-icon class="sidebar-icon"></table-icon>
                        Manifestations
                    </router-link>
                </li>
            </ul>
            
            <logOutButton class="logout-sidebar-button"></logOutButton> 
        </div>
    </nav>
    `,

    data: function() {
        return {};
    },

    methods: {
    },

    mounted() {
    },

    destroyed() {}
});
