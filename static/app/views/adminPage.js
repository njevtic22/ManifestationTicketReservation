Vue.component("adminPage", {
    template: `
    <div class="d-flex" id="wrapper" v-bind:class="{toggled: isToggled}">

        <!-- Sidebar -->
        <div class="bg-light border-right" id="sidebar-wrapper">
            <adminSidebar></adminSidebar>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-lg">
                <button class="btn btn-outline-light" id="menu-toggle" v-on:click="toggleSidebar">
                    <list-ul-icon></list-ul-icon>
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
    }
});
