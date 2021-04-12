Vue.component("manifestationOptions", {
    template: `
    <div class="dropdown">
        <button class="btn btn-secondary btn-block dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Options
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
            <button 
                class="btn btn-success btn-block" 
                v-if="manifestation.status == 'CREATED'"
                v-on:click="$emit('approve', manifestation.id)"
            >
                Approve
            </button>
            <button 
                class="btn btn-primary btn-block" 
                v-if="manifestation.status == 'CREATED'"
                v-on:click="$emit('reject', manifestation.id)"
            >
                Reject
            </button>
            <button 
                class="btn btn-outline-danger btn-block" 
                v-if="manifestation.hasEnded && manifestation.status == 'ACTIVE'"
                v-on:click="$emit('end', manifestation.id)"
            >
                End manifestation
            </button>

            <button
                id="deleteManifestationBtn" 
                class="btn btn-danger btn-block"
                data-toggle="modal"
                v-bind:data-target="'#deleteManifestationModal' + manifestation.id"
            >
                Delete manifestation
            </button>
        </div>

        <deleteManifestationModal 
            v-bind:id="'deleteManifestationModal' + manifestation.id"
            ref="deleteManifestationModal"
            
            v-bind:manifestationToDelete="manifestation"

            v-on:deleteManifestation="$emit('deleteManifestation', $event)"
        >
        </deleteManifestationModal>
    </div>
    `,

    props: {
        manifestation: {
            type: Object,
            required: true
        }
    },

    data: function() {
        return {};
    },

    methods: {
        deleteManifestation: function() {
            $("#deleteManifestationModal").modal("show");
        }
    },

    mounted() {},

    destroyed() {}
});
