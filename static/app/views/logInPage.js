Vue.component("logInPage", {
    template: `
    <div class="d-flex" id="wrapper" v-bind:class="{toggled: isToggled}">

        <!-- Sidebar -->
        <div class="bg-light border-right" id="sidebar-wrapper">
            <anonymousSidebar></anonymousSidebar>
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
                <loginForm></loginForm>
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
        }
    },

    mounted() {
    },

    destroyed() {}
});
