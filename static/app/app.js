const LoginPage = { template: "<logInPage></logInPage>" };
const RegisterPage = { template: "<registerPage></registerPage>" };
const NotFoundPage = { template: "<notFoundPage></notFoundPage>" };
const ForbiddenPage = { template: "<forbiddenPage></forbiddenPage>" };
const UnauthorizedPage = { template: "<unauthorizedPage></unauthorizedPage>" };
const ManifestationsPage = { template: "<manifestationsPage></manifestationsPage>" };
const ManifestationsTable = { template: "<manifestationsTable></manifestationsTable>" };
const ManifestationsMap = { template: "<manifestationsMap></manifestationsMap>" };
const AdminPage = { template: "<adminPage></adminPage>" };
const SalesmanPage = { template: "<salesmanPage></salesmanPage>" };
const CustomerPage = { template: "<customerPage></customerPage>" };
const Profile = { template: "<profile></profile>" }

const UserRoles = Object.freeze({
    ADMIN: "ADMIN",
    SALESMAN: "SALESMAN",
    CUSTOMER: "CUSTOMER",
    ANONYMOUS: "ANONYMOUS"
});


const isAdminRoute = (to, from, next) => {
    if (localStorage.getItem("role") === UserRoles.ADMIN) {
        next();
        return;
    }
    next({
        name: "UnauthorizedPage"
    })
};

const isSalesmanRoute = (to, from, next) => {
    if (localStorage.getItem("role") === UserRoles.SALESMAN) {
        next();
        return;
    }
    next({
        name: "UnauthorizedPage"
    })
};

const isCustomerRoute = (to, from, next) => {
    if (localStorage.getItem("role") === UserRoles.CUSTOMER) {
        next();
        return;
    }
    next({
        name: "UnauthorizedPage"
    })
};

const isLogOut = (to, from, next) => {
    if (!localStorage.getItem("role")) {
        next();
        return;
    }
    next({
        name: "UnauthorizedPage"
    })
};

const isLogIn = (to, from, next) => {
    if (localStorage.getItem("role")) {
        next();
        return;
    }
    next({
        name: "UnauthorizedPage"
    })
};

const errorPageUserCheck = (to, from, next) => {
    if (localStorage.getItem("role")) {
        next();
        return;
    }
    next({
        name: "LogInPage"
    })
};

const router = new VueRouter({
    mode: "hash",
    routes: [
        {
            path: "/login",
            name: "LogInPage",
            component: LoginPage,
            beforeEnter: isLogOut,
            meta: { title: "Login" }
        },
        {
            path: "/register",
            name: "RegisterPage",
            component: RegisterPage,
            beforeEnter: isLogOut,
            meta: { title: "Register" }
        },
        {
            path: "/",
            name: "ManifestationsPage",
            component: ManifestationsPage,
            beforeEnter: isLogOut,
            children: [
                {
                    path: "manifestations/table",
                    name: "AnonymousManifestationsTable",
                    component: ManifestationsTable,
                    meta: { title: "Manifestations table" }
                },
                {
                    path: "manifestations/map",
                    name: "AnonymousManifestationsMap",
                    component: ManifestationsMap,
                    meta: { title: "Manifestations map" }
                }
            ],
        },
        {
            path: "/admin",
            name: "AdminPage",
            component: AdminPage,
            beforeEnter: isAdminRoute,
            children: [
                {
                    path: "profile",
                    name: "AdminProfile",
                    component: Profile,
                    meta: { title: "Profile" }
                },
                {
                    path: "manifestations/table",
                    name: "AdminManifestationsTable",
                    component: ManifestationsTable,
                    meta: { title: "Manifestations table" }
                },
                {
                    path: "manifestations/map",
                    name: "AdminManifestationsMap",
                    component: ManifestationsMap,
                    meta: { title: "Manifestations map" }
                }
            ],
        },
        {
            path: "/salesman",
            name: "SalesmanPage",
            component: SalesmanPage,
            beforeEnter: isSalesmanRoute,
            children: [
                {
                    path: "profile",
                    name: "SalesmanProfile",
                    component: Profile,
                    meta: { title: "Profile" }
                },
                {
                    path: "manifestations/table",
                    name: "SalesmanManifestationsTable",
                    component: ManifestationsTable,
                    meta: { title: "Manifestations table" }
                },
                {
                    path: "manifestations/map",
                    name: "SalesmanManifestationsMap",
                    component: ManifestationsMap,
                    meta: { title: "Manifestations map" }
                }
            ]
        },
        {
            path: "/customer",
            name: "CustomerPage",
            component: CustomerPage,
            beforeEnter: isCustomerRoute,
            children: [
                {
                    path: "profile",
                    name: "CustomerProfile",
                    component: Profile,
                    meta: { title: "Profile" }
                },
                {
                    path: "manifestations/table",
                    name: "CustomerManifestationsTable",
                    component: ManifestationsTable,
                    meta: { title: "Manifestations table" }
                },
                {
                    path: "manifestations/map",
                    name: "CustomerManifestationsMap",
                    component: ManifestationsMap,
                    meta: { title: "Manifestations map" }
                }
            ]
        },
        {
            path: "/401",
            name: "UnauthorizedPage",
            component: UnauthorizedPage,
            beforeEnter: errorPageUserCheck,
            meta: { title: "Unauthorized" }
        },
        {
            path: "/403",
            name: "ForbiddenPage",
            component: ForbiddenPage,
            beforeEnter: errorPageUserCheck,
            meta: { title: "Forbidden" }
        },
        {
            path: "/404",
            name: "NotFoundPage",
            alias: "*",
            component: NotFoundPage,
            beforeEnter: errorPageUserCheck,
            meta: { title: "Not Found" }
        }
    ]
});

// router.beforeEach((to, from, next) => {
//     // let userRole = localStorage.getItem("role");
//     // if (userRole == null) {
//     //     userRole = "ANONYMOUS";
//     // }

//     // if (to.meta.allow == null) {
//     //     next({
//     //         name: "NotFoundPage"
//     //     });
//     // } else if (to.meta.allow.includes(userRole)) {
//     //     next();
//     // } else {
//     //     next({
//     //         name: "NotFoundPage"
//     //     });
//     // }
//     next();
// });

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

        redirectToUserPage: function() {
            if (!this.isUserLoggedIn()) {
                this.$router.push({
                    name: "ManifestationsPage"
                })
            } else if (this.isAdmin()) {
                this.$router.push({
                    name: "AdminManifestationsTable"
                })
            } else if (this.isSalesman()) {
                this.$router.push({
                    name: "SalesmanManifestationsTable"
                })
            } else if (this.isCustomer()) {
                this.$router.push({
                    name: "CustomerManifestationsTable"
                })
            } else {
                alert("Serious error. This should not happen");
                this.clearStorageAndHeader();
                this.$router.push({
                    name: "LogInPage"
                })
            }
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
