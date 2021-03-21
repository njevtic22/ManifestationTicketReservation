Vue.component("unauthorizedPage", {
    template:
    `
    <div class="d-flex" id="wrapper" v-bind:class="{toggled: isToggled}">

        <!-- Sidebar -->
        <div class="bg-light border-right" id="sidebar-wrapper">
            <sideBar></sideBar>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <nav class="navbar navbar-expand-lg spaced">
                <button class="btn btn-outline-dark" id="menu-toggle" v-on:click="toggleSidebar">
                    <list-ul-icon></list-ul-icon>
                </button>

                <h3 class="px-3 text-dark pageTitle">{{$route.meta.title}}</h3>
            </nav>

            <div class="container-fluid main-content">
                <div class="error-center">
                    <img 
                    src="/images/401 error.png" 
                    alt="Even image is unauthorized" 
                    width="280" height="210"/>
                    <h1 class="text-danger">Unauthorized acces</h1>            
                </div>
            </div>
        </div>
        <!-- /#page-content-wrapper -->

    </div>
    `,
    data: function() {
        return {
          isToggled: false
        };
    },

    methods: {
        toggleSidebar: function() {
            this.isToggled = !this.isToggled;
        },

        // toggleSidebarr: function() {
        //     $("#wrapper").toggleClass("toggled");
        // }
    },

    mounted() {
    }
})