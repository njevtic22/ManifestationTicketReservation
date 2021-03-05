Vue.component("profile", {
    template: `
    <div class="container emp-profile">
        <div class="row">
            <div class="col-md-4">
                <div class="profile-img">
                    <img src=".\\images\\default user logo.png" alt="Image not found"/>
                </div>
            </div>
            <div class="col-md-6">
                <div class="profile-head">
                    <h1>
                        {{user.name}} {{user.surname}}
                    </h1>
                    <h4>
                        {{user.role}}
                    </h4>
                </div>
            </div>
            <div class="col-md-2">
                <button 
                    type="button" 
                    class="profile-edit-btn"
                    data-toggle="modal" 
                    data-target="#editProfileModalId"
                >
                    Edit Profile
                </button>
                <p></p>

                <button 
                    type="button" 
                    class="profile-edit-btn"
                    data-toggle="modal" 
                    data-target="#changePasswordModalId"
                >
                    Change password
                </button>
            </div>
        </div>
        <div class="row">
            <div class="profile-work col-md-4">
            </div>
            <div class="col-md-8">
                <div class="tab-content profile-tab" id="myTabContent">
                    <div class="tab-pane fade show active" id="details">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Username</label>
                            </div>
                            <div class="col-md-6">
                                <p>{{user.username}}</p>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <label>Date of birth</label>
                            </div>
                            <div class="col-md-6">
                                <p>{{user.dateOfBirth}}</p>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <label>Gender</label>
                            </div>
                            <div class="col-md-6">
                                <p>{{user.gender}}</p>
                            </div>
                        </div>

                        <div class="row" v-if="$root.isCustomer()">
                            <div class="col-md-6">
                                <label>Type</label>
                            </div>
                            <div class="col-md-6">
                                <p>{{user.type}}</p>
                            </div>
                        </div>

                        <div class="row" v-if="$root.isCustomer()">
                            <div class="col-md-6">
                                <label>Points</label>
                            </div>
                            <div class="col-md-6">
                                <p>{{user.points}}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <editProfileModal 
            id="editProfileModalId"
            v-on:changedProfile="getUser"
        ></editProfileModal>
        <changePasswordModal 
            id="changePasswordModalId"
        ></changePasswordModal>

        <userService ref="userService"></userService>
    </div>
    `,

    data: function() {
        return {
            user: {
                id: 0,
                name: "",
                surname: "",
                username: "",
                dateOfBirth: "",
                gender: "",
                role: "",

                type: "",
                points: 0,
            }
        };
    },

    methods: {
        getUser: function() {
            const successCallback = (response) => {
                this.user = response.data;
            };

            const errorCallback = (error) => {
                this.$root.defaultCatchError(error);
            };

            this.$refs.userService.getAuthenticated(successCallback, errorCallback);  
        }
    },

    mounted() {
        this.getUser();
    },

    destroyed() {}
});
