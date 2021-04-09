Vue.component("all-marker", {
    template: `
    <ymap-marker
        v-bind:markerId="manifestation.id"
        v-bind:coords="[manifestation.location.latitude, manifestation.location.longitude]""

        v-bind:balloon-template="balloonTemplate"
        
        v-on:balloonopen="bindListener"
        v-on:balloonclose="unbindListener"
    />
    `,

    props: {
        manifestation: Object
    },

    data: function() {
        return {
            imageToShow: "images/no image 2.png"
        };
    },

    computed: {
        balloonTemplate() {
            return `
            <img src="${this.imageToShow}" style="width: 100%;">
            <h3>${this.manifestation.name}</h3>
            <button id="${this.manifestation.id.toString() + "btn"}" class="btn btn-primary">View details</button>
            `;
        },
        
        formattedAddress() {
            const address = this.manifestation.location.address;
            return address.street + " " + address.number + ", " + address.city + ", " + address.postalCode;
        }
    },

    methods: {
        bindListener() {
            document.getElementById(`${this.manifestation.id.toString() + "btn"}`)
                .addEventListener('click', this.redirectToManifestation);
        },
        unbindListener() {
            document.getElementById(`${this.manifestation.id.toString() + "btn"}`)
                .removeEventListener('click', this.redirectToManifestation);
        },

        redirectToManifestation: function() {
            // // NOTE: relies on current route
            // const lastIndex = this.$route.path.lastIndexOf("/");
            // const pathTo = this.$route.path.substring(0, lastIndex + 1) + this.manifestation.id;
            // this.$router.push({ path: pathTo });


            // NOTE: relies on roles
            let rolePath = "";
            if (this.$root.isAdmin())
                rolePath = "/admin";
            else if (this.$root.isSalesman())
                rolePath = "/salesman";
            else if (this.$root.isCustomer())
                rolePath = "/customer";

            
            const pathTo = `${rolePath}/manifestations/${this.manifestation.id}`;
            this.$router.push({ path: pathTo });
        }
    },

    mounted() {
        this.imageToShow = `data:image/${this.manifestation.imageType};base64, ${this.manifestation.imageBase64}`;
    },

    destroyed() {}
});
