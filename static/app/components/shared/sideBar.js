Vue.component("sideBar", {
    template: `
    <nav class="col-md-2 bg-light sidebar">
      <div class="sidebar-sticky">
        <ul class="nav flex-column">
          <!-- <li class="nav-item">
            <router-link class="nav-link text-dark" to="/profile">
              Profile
            </router-link>
          </li>

          <li class="nav-item">
            <router-link class="nav-link text-dark" to="/home">
              Virtual Machines
            </router-link>
          </li>

          <li class="nav-item" v-if="$root.isSuperAdmin()">
            <router-link class="nav-link text-dark" to="/organizations">
              Organizations
            </router-link>
          </li>

          <li class="nav-item" v-if="!$root.isRegularUser()">
            <router-link class="nav-link text-dark" to="/usersPage">
              Users
            </router-link>
          </li>

          <li class="nav-item">
            <router-link class="nav-link text-dark" to="/drivesPage">
              Drives
            </router-link>
          </li>
          
          <li class="nav-item" v-if="$root.isSuperAdmin()">
            <router-link class="nav-link text-dark" to="/categoriesPage">
              Categories
            </router-link>
          </li> -->

          <logOutButton v-if="$root.isUserLoggedIn()" class="logout-sidebar-button"></logOutButton>
        </ul> 
      </div>
    </nav>
    `,

    data: function() {
        return {};
    },

    methods: {},

    mounted() {}
});
