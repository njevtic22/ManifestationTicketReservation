const LoginPage = { template: "<logInPage></logInPage>" };
const RegisterPage = { template: "<registerPage></registerPage>" };
const NotFoundPage = { template: "<notFoundPage></notFoundPage>" };
const ForbiddenPage = { template: "<forbiddenPage></forbiddenPage>" };
const UnauthorizedPage = { template: "<unauthorizedPage></unauthorizedPage>" };
const ManifestationsPage = {
    template: "<manifestationsPage></manifestationsPage>"
};

const UserRoles = Object.freeze({
    ADMIN: "ADMIN",
    SALESMAN: "SALESMAN",
    CUSTOMER: "CUSTOMER",
    ANONYMOUS: "ANONYMOUS"
});

const router = new VueRouter({
    mode: "hash",
    routes: [
        {
            path: "/login",
            name: "LogInPage",
            component: LoginPage,
            meta: { 
                title: "Login page",
                allow: [UserRoles.ANONYMOUS]
         }
        },
        {
            path: "/register",
            name: "RegisterPage",
            component: RegisterPage,
            meta: { 
                title: "Register page",
                allow: [UserRoles.ANONYMOUS]
            }
        },
        {
            path: "/",
            name: "ManifestationsPage",
            component: ManifestationsPage,
            meta: { 
                title: "Manifestations page",
                allow: [UserRoles.ANONYMOUS, UserRoles.ADMIN, UserRoles.SALESMAN, UserRoles.CUSTOMER]
            }
        },
        {
            path: "/401",
            name: "UnauthorizedPage",
            component: UnauthorizedPage,
            meta: {
                title: "Unauthorized",
                allow: [UserRoles.ADMIN, UserRoles.SALESMAN, UserRoles.CUSTOMER]
            }
        },
        {
            path: "/403",
            name: "ForbiddenPage",
            component: ForbiddenPage,
            meta: {
                title: "Forbidden",
                allow: [UserRoles.ADMIN, UserRoles.SALESMAN, UserRoles.CUSTOMER]
            }
        },
        {
            path: "/404",
            name: "NotFoundPage",
            alias: "*",
            component: NotFoundPage,
            meta: {
                title: "Not Found",
                allow: [UserRoles.ADMIN, UserRoles.SALESMAN, UserRoles.CUSTOMER]
            }
        }
    ]
});

router.beforeEach((to, from, next) => {
    console.log("nemanja");
    console.log(to);
    let userRole = localStorage.getItem("role");
    if (userRole == null) {
        userRole = "ANONYMOUS";
    }


    if (to.meta.allow.includes(userRole)) {
        next();
    } else {
        next({
            name: "NotFoundPage"
        });
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
