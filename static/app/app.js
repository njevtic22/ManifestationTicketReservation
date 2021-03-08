const ActiveAndInactiveManifestationsReg = { template: "<activeAndInactiveManifestationsReg></activeAndInactiveManifestationsReg>" };
const ActiveAndInactiveManifestationsMap = { template: "<activeAndInactiveManifestationsMap></activeAndInactiveManifestationsMap>" };
const Profile = { template: "<profile></profile>" };
const AllUsers = { template: "<allUsers></allUsers>" }

const LoginPage = { template: "<logInPage></logInPage>" };
const RegisterPage = { template: "<registerPage></registerPage>" };
const NotFoundPage = { template: "<notFoundPage></notFoundPage>" };
const ForbiddenPage = { template: "<forbiddenPage></forbiddenPage>" };
const UnauthorizedPage = { template: "<unauthorizedPage></unauthorizedPage>" };


const beforeRouteEnterAndUpdateForUserPages = (to, from, next, pathToCheck) => {
    if (to.path === `${pathToCheck}` || to.path === `${pathToCheck}/`) {
        next(`${pathToCheck}/manifestations/table`);
        return;
    }
    if (to.path.startsWith(`${pathToCheck}`)) {
        next();
        return;
    }
    next(`${pathToCheck}/manifestations/table`);
}; 

const ManifestationsPage = { 
    template: "<manifestationsPage></manifestationsPage>",
    beforeRouteEnter (to, from, next) {
        beforeRouteEnterAndUpdateForUserPages(to, from, next, "");
    },
    beforeRouteUpdate (to, from, next) {
        beforeRouteEnterAndUpdateForUserPages(to, from, next, "");
    }
};
const AdminPage = { 
    template: "<adminPage></adminPage>",
    beforeRouteEnter (to, from, next) {
        beforeRouteEnterAndUpdateForUserPages(to, from, next, "/admin");
    },
    beforeRouteUpdate (to, from, next) {
        beforeRouteEnterAndUpdateForUserPages(to, from, next, "/admin");
    }
};
const SalesmanPage = { 
    template: "<salesmanPage></salesmanPage>",
    beforeRouteEnter (to, from, next) {
        beforeRouteEnterAndUpdateForUserPages(to, from, next, "/salesman");
    },
    beforeRouteUpdate (to, from, next) {
        beforeRouteEnterAndUpdateForUserPages(to, from, next, "/salesman");
    }
};
const CustomerPage = { 
    template: "<customerPage></customerPage>",
    beforeRouteEnter (to, from, next) {
        beforeRouteEnterAndUpdateForUserPages(to, from, next, "/customer");
    },
    beforeRouteUpdate (to, from, next) {
        beforeRouteEnterAndUpdateForUserPages(to, from, next, "/customer");
    }
};

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
    console.log("errorPageUserCheck");
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
                    component: ActiveAndInactiveManifestationsReg,
                    meta: { title: "Manifestations table" }
                },
                {
                    path: "manifestations/map",
                    name: "AnonymousManifestationsMap",
                    component: ActiveAndInactiveManifestationsMap,
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
                    component: ActiveAndInactiveManifestationsReg,
                    meta: { title: "Manifestations table" }
                },
                {
                    path: "manifestations/map",
                    name: "AdminManifestationsMap",
                    component: ActiveAndInactiveManifestationsMap,
                    meta: { title: "Manifestations map" }
                },
                {
                    path: "users",
                    name: "AllUsers",
                    component: AllUsers,
                    meta: { title: "Users" }
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
                    component: ActiveAndInactiveManifestationsReg,
                    meta: { title: "Manifestations table" }
                },
                {
                    path: "manifestations/map",
                    name: "SalesmanManifestationsMap",
                    component: ActiveAndInactiveManifestationsMap,
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
                    component: ActiveAndInactiveManifestationsReg,
                    meta: { title: "Manifestations table" }
                },
                {
                    path: "manifestations/map",
                    name: "CustomerManifestationsMap",
                    component: ActiveAndInactiveManifestationsMap,
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
        <myToast ref="toast"></myToast>
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

        getTimeFormat: function() {
            return "HH:mm:ss";
        },

        getDateFormat: function() {
            return "DD.MM.YYYY.";
        },

        getDateTimeFormat: function() {
            return this.getDateFormat() + " " + this.getTimeFormat();
        },

        successToast: function(message, timeout=5000) {
            this.$refs.toast.showSuccess(message, timeout);
        },
        
        failureToast: function(message, timeout=5000) {
            this.$refs.toast.showFailure(message, timeout);
        },

        infoToast: function(message, timeout=5000) {
            this.$refs.toast.showInfo(message, timeout);
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
                this.failureToast("Error: " + status + ": " + msg);
                this.clearStorageAndHeader();
                this.$router.push(err.response.headers.redirect);
            } else if (
                statusString == "401" ||
                statusString == "403" ||
                statusString == "404"
            ) {
                this.failureToast("Error: " + status + ": " + msg);
                // there should be header redirect in response from server
                this.$router.push(err.response.headers.redirect);
            } else {
                this.failureToast("Error: " + status + ": " + msg);
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
