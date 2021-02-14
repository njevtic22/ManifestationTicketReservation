const LoginPage = { template: "<logInPage></logInPage>" };
const NotFoundPage = { template: "<notFoundPage></notFoundPage>" };
const ForbiddenPage = { template: "<forbiddenPage></forbiddenPage>" };
const UnauthorizedPage = { template: "<unauthorizedPage></unauthorizedPage>" };
const ManifestationsTablePage = {
    template: "<manifestationsTablePage></manifestationsTablePage>"
};

const router = new VueRouter({
    mode: "hash",
    routes: [
        {
            path: "/login",
            name: "LogInPage",
            component: LoginPage,
            meta: { title: "Login page", requiresAuth: false }
        },
        {
            path: "/",
            name: "ManifestationsTablePage",
            component: ManifestationsTablePage,
            meta: { title: "Manifestations page", requiresAuth: false }
        },

        {
            path: "/401",
            name: "UnauthorizedPage",
            component: UnauthorizedPage,
            meta: {
                title: "Unauthorized",
                requiresAuth: true,
                allow: ["ADMIN", "SALESMAN", "CUSTOMER"]
            }
        },
        {
            path: "/403",
            name: "ForbiddenPage",
            component: ForbiddenPage,
            meta: {
                title: "Forbidden",
                requiresAuth: true,
                allow: ["ADMIN", "SALESMAN", "CUSTOMER"]
            }
        },
        {
            path: "/404",
            name: "NotFoundPage",
            alias: "*",
            component: NotFoundPage,
            meta: {
                title: "Not Found",
                requiresAuth: true,
                allow: ["ADMIN", "SALESMAN", "CUSTOMER"]
            }
        }
    ]
});

router.beforeEach((to, from, next) => {
    let userRole = localStorage.getItem("role");

    let isUserLoggedIn = function() {
        return userRole !== null;
    };

    if (!isUserLoggedIn()) {
        // user is not logged in

        if (!to.meta.requiresAuth) {
            next();
        } else {
            next({
                name: "ManifestationsTablePage"
            });
        }
    } else {
        // user is logged in
        if (!to.meta.requiresAuth) {
            if (to.name === "LogInPage") {
                next({
                    name: "ManifestationsTablePage"
                });
            } else {
                next();
            }
        } else {
            if (to.meta.allow.includes(userRole)) {
                next();
            } else {
                next({
                    name: "NotFoundPage"
                });
                // toast("Serious error. This should not happen");
                // next(from.name);
            }
        }
    }
});

var app = new Vue({
    el: "#ManifestationTicketReservation",
    template: `
    <div>
        <router-view />
        <myToast></myToast>
    </div>
    `,
    router,

    methods: {
        isAdmin: function() {
            return localStorage.getItem("role") == "ADMIN";
        },

        isSalesman: function() {
            return localStorage.getItem("role") == "SALESMAN";
        },

        isCustomer: function() {
            return localStorage.getItem("role") == "CUSTOMER";
        },

        isUserLoggedIn: function() {
            return localStorage.getItem("role") !== null;
        },

        clearStorageAndHeader: function() {
            localStorage.clear();
            sessionStorage.clear();
            delete axios.defaults.headers.common["Authorization"];
        },

        defaultCatchError: function(err) {
            const status = err.response.status;
            const msg = err.response.data;

            const statusString = status.toString();

            if (statusString[0] == "3") {
                toast("Error: " + status + ": " + msg, "failure");
                this.clearStorageAndHeader();
                this.$router.push(err.response.headers.redirect);
            } else if (
                statusString == "401" ||
                statusString == "403" ||
                statusString == "404"
            ) {
                toast("Error: " + status + ": " + msg, "failure");
                // there should header redirect in response from server
                this.$router.push(err.response.headers.redirect);
            } else {
                toast("Error: " + status + ": " + msg);
            }
        }
    },

    created() {
        // make axios send token as default header
        axios.defaults.headers.common["Authorization"] =
            "Bearer " + localStorage.getItem("token");

        // axios.defaults.headers.common['Content-Type'] = "application/json";
    }
});
