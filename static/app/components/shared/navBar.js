Vue.component("navBar", {
    template: `
    <nav class="navbar navbar-dark fixed-top bg-dark p-0 shadow">
      <a class="text-center navbar-brand col-sm-3 col-md-2 mr-0" href="#/"><h5>Manifestation service</h5></a>
      <h5 class="px-3 text-light">{{$route.meta.title}}</h5>
    </nav>
    `,

    data: function() {
        return {};
    },

    methods: {},

    mounted() {}
});
