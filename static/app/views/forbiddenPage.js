Vue.component("forbiddenPage", {
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

            <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
                <button class="btn btn-outline-light" id="menu-toggle" v-on:click="toggleSidebar">
                    <list-ul-icon></list-ul-icon>
                </button>

                <h5 class="px-3 text-light pageTitle">{{$route.meta.title}}</h5>
            </nav>

            <div class="container-fluid">
                <main role="main">
                  
                <div class="error-center">
                    <img 
                    src="/images/403 error.png" 
                    alt="Even image is forbidden" 
                    width="280" height="210"/>
                    <h1 class="text-danger">Forbidden acces</h1>            
                </div>

                </main>
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
});