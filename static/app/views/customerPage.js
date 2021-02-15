Vue.component("customerPage", {
    template: `
    <div class="d-flex" id="wrapper" v-bind:class="{toggled: isToggled}">

        <!-- Sidebar -->
        <div class="bg-light border-right" id="sidebar-wrapper">
            <customerSidebar></customerSidebar>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
                <button class="btn btn-outline-light" id="menu-toggle" v-on:click="toggleSidebar">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-ul" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm-3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                    </svg>
                </button>

                <h5 class="px-3 text-light pageTitle">{{$route.meta.title}}</h5>
            </nav>

            <div class="container-fluid">
                <main role="main">
                    <router-view></router-view>
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
      console.log("Customer page mounted");
    }
});
