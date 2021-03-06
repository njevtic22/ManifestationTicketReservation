Vue.component("salesmanService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            baseSalesmenUrl: "/api/salesmen"
        };
    },

    methods: {
        // getSalesman: function(salesmanId, successCallback, errorCallback) {
        //     const url = `${this.baseSalesmenUrl}/${salesmanId}`;
        //     axios
        //         .get(url)
        //         .then(response => { successCallback(response); })
        //         .catch(error => { errorCallback(error); });
        // },

        updateSalesman: function(salesmanId, salesmanToUpdate, successCallback, errorCallback) {
            const url = `${this.baseSalesmenUrl}/${salesmanId}`;
            axios
                .put(url, salesmanToUpdate)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error) });
        },

        deleteSalesman: function(salesmanId, successCallback, errorCallback) {
            const url = `${this.baseSalesmenUrl}/${salesmanId}`;
            axios
                .delete(url)
                .then(response => { successCallback(response); })
                .catch(error => { errorCallback(error); });

                // url, {
                //     data: {
                //         id: self.chosenUser.id,
                //         name: self.chosenUser.name,
                //         surname: self.chosenUser.surname,
                //         email: self.chosenUser.email,
                //         password: self.chosenUser.password,
                //         organization: self.chosenUser.organization,
                //         role: self.chosenUser.role
                //     }
                // }
        }
    },

    mounted() {},

    destroyed() {}
});
