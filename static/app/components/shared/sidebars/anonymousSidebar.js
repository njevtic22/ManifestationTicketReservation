Vue.component("anonymousSidebar", {
    template: `
    <nav class="bg-light list-group list-group-flush sidebar"> 

        <div class="sidebar-sticky">
            <h5 class="sidebar-heading"><a href="#/" id="mainAnchor">Manifestation service</a></h5>
            <ul class="nav flex-column">

                <li class="nav-item">
                    <router-link class="nav-link" to="/login">
                        <box-arrow-in-right-icon class="sidebar-icon"></box-arrow-in-right-icon>
                        Log in
                    </router-link>
                </li>

                <li class="nav-item">
                    <router-link class="nav-link" to="/register">
                        <box-arrow-in-right-icon class="sidebar-icon"></box-arrow-in-right-icon>
                        Register
                    </router-link>
                </li>

                <li class="nav-item">
                    <router-link class="nav-link" to="/manifestations/table">
                        <table-icon class="sidebar-icon"></table-icon>
                        Manifestations
                    </router-link>
                </li>
            </ul> 
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
