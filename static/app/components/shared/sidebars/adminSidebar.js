Vue.component("adminSidebar", {
    template: `
    <nav class="bg-light list-group list-group-flush sidebar"> 
        <h5 class="sidebar-heading titleBottom"><a href="#/" id="mainAnchor">Manifestation service</a></h5>
    
        <div class="sidebar-sticky">
            <ul class="nav flex-column">
    
                <li class="nav-item">
                    <router-link class="nav-link" to="/admin/profile">
                        <span class="sidebar-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                            </svg>
                        </span>
                        Profile
                    </router-link>
                </li>
                
                <li class="nav-item">
                    <router-link class="nav-link" to="/admin/manifestations/table">
                        <span class="sidebar-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-table" viewBox="0 0 16 16">
                                <path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm15 2h-4v3h4V4zm0 4h-4v3h4V8zm0 4h-4v3h3a1 1 0 0 0 1-1v-2zm-5 3v-3H6v3h4zm-5 0v-3H1v2a1 1 0 0 0 1 1h3zm-4-4h4V8H1v3zm0-4h4V4H1v3zm5-3v3h4V4H6zm4 4H6v3h4V8z"/>
                            </svg>
                        </span>
                        Manifestations
                    </router-link>
                </li>
    
    
    
                <logOutButton class="logout-sidebar-button"></logOutButton>
            </ul> 
        </div>
    </nav>
    `,

    data: function() {
        return {};
    },

    methods: {
    },

    mounted() {},

    destroyed() {}
});
