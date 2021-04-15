Vue.component("deleteManifestationModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-danger"
        btnSuccessClass="btn-danger"
        modalTitle="Delete manifestation"
        modalClass=""
        successBtnText="Delete"
        cancelBtnText="Cancel"

        v-on:successEvent="deleteManifestation"
        v-on:cancelEvent="cancel"
    >
        <img 
            v-bind:src="imageToShow" 
            class="card-img-top" 
            alt="Image not found"
        />
        <div 
            class="card-body d-flex flex-column"
        >
            <div class="d-flex justify-content-between">
                <h5 class="card-title">{{ manifestationToDelete.name }}</h5>
                <p class="card-title btn text-white" v-bind:style="{'background-color': statusColor}">{{ manifestationToDelete.status }}</p>
            </div>
            <div class="d-flex justify-content-between">
                <h5 class="card-title">{{ manifestationToDelete.type }}</h5>

                <h5 class="card-title" v-if="manifestationToDelete.status == 'INACTIVE'">Rating: {{ parseRating }}</h5>
                <h5 class="card-title" v-else></h5>
            </div>
            
            <h6 class="text-left">Regular ticket price: {{ manifestationToDelete.regularTicketPrice }} RSD</h6>

            <!--
            <hr/>
            <p 
                class="
                    card-text 
                    scroll 
                    scroll-invisible 
                    description-scroll
                    text-left
                    "
            >
                {{ manifestationToDelete.description }}
            </p>
            <hr/>
            -->

            <br/>
            <div class="d-flex justify-content-between mt-auto">
                <em style="margin-right: 10px;">{{ formattedAddress }}</em>
                <em>{{ manifestationToDelete.holdingDate }}</em>
            </div>
            
            <hr/>
            <div class="text-left">
                Are you sure you want to permanently delete this manifestation?
                <br/>
                <strong>This action can not be undode.</strong>
            </div>
        </div>
    </baseModal>
    `,

    props: {
        id: String,
        manifestationToDelete: {
            type: Object,
            required: true
        },
    },

    data: function() {
        return {
            /*
            manifestationToDelete: { 
                id: 0, 
                name: "",
                regularTicketPrice: 0,
                holdingDate: "",
                status: "CREATED",
                type: "CONCERT",

                imageBase64: "",
                imageType: ""
            },
            */
            statusColor: "",

            StatusColors: Object.freeze({
                CREATED: "#0000A0",     // DarkBlue
                REJECTED: "#FF8C00",    // DarkOrange
                ACTIVE: "#006400",      // DarkGreen
                INACTIVE: "#8B0000"     // DarkRed
            })
        };
    },

    methods: {
        deleteManifestation: function() {
            this.closeModal();
            this.$emit('deleteManifestation', this.manifestationToDelete.id);
        },

        cancel: function() {
            this.closeModal();
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {

        }
    },

    computed: {
        imageToShow() {
            return `data:image/${this.manifestationToDelete.imageType};base64, ${this.manifestationToDelete.imageBase64}`;
        },
        
        formattedAddress() {
            const address = this.manifestationToDelete.location.address;
            return address.street + " " + address.number + ", " + address.city + ", " + address.postalCode;
        },
        
        parseRating() {
            // const roundNumber = Math.round(this.manifestationToDelete.avgRating * 100) / 100;
            // const roundNumber = Number(this.manifestationToDelete.avgRating);
            // const roundString = roundNumber.toFixed(2);
            return this.manifestationToDelete.avgRating.toFixed(2);
        }
    },

    mounted() {
        this.statusColor = this.StatusColors[this.manifestationToDelete.status];
    },

    destroyed() {}
});
