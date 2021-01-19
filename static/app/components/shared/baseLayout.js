Vue.component("baseLayout", {
    template: `
    <div>
        <navBar></navBar>
        <div class="container-fluid">
            <div class="row">
                <sideBar></sideBar>
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3"></div>
                    <div class="container" id="container">
                        <slot></slot>
                    </div>
                </main>
            </div>
        </div>
    </div>
    `,
    data: function() {
        return {};
    },

    methods: {},

    mounted() {}
});
