Vue.component("baseLayout", {
    template: `
    <div class="d-flex" id="wrapper">
  
      <!-- Sidebar -->
      <div class="bg-light border-right" id="sidebar-wrapper">
        <h5 class="sidebar-heading titleBottom"><a href="#/" id="mainAnchor">Manifestation service</a></h5>
        <sideBar></sideBar>
      </div>
      <!-- /#sidebar-wrapper -->
  
      <!-- Page Content -->
      <div id="page-content-wrapper">
  
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
          <button class="btn btn-outline-light" id="menu-toggle" v-on:click="toggleSidebar">

          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-justify" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M2 12.5a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>
          </svg>

          </button>

          <button type="button" value="Log in" v-on:click="failureToast" class="btn btn-danger">Failure toast</button>	
          <button type="button" value="Log in" v-on:click="successToast" class="btn btn-success">Success toast</button>	
	

          <h5 class="px-3 text-light pageTitle">{{$route.meta.title}}</h5>
        </nav>
  
        <div class="container-fluid">
            <main role="main">
                <div class="container" id="container">
                    <slot></slot>
                </div>
            </main>
        </div>
      </div>
      <!-- /#page-content-wrapper -->

    </div>
    `,
    data: function() {
        return {};
    },

    methods: {
        toggleSidebar: function() {
            console.log("nemanja");
            $("#wrapper").toggleClass("toggled");
        },

        
        failureToast: function() {
            this.$root.$emit("toastFailure", "This toast failed");
        },

        successToast: function() {
            this.$root.$emit("toastSuccess", "This toast succeeded");
        },
    },

    mounted() {}
});
