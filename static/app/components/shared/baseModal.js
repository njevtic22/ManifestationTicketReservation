Vue.component("baseModal", {
    template: `
    <div 
        class="modal fade"
        v-bind:id="id"
        data-keyboard="false"
        tabindex="-1"
        role="dialog"
        data-backdrop="static"
        aria-labelledby="modalTitle"
    >
        <div class="modal-dialog modal-dialog-scrollable" role="document">
            <div class="modal-content">
                <div class="modal-header" v-bind:class="[headerClass]">
                    <h5 class="modal-title text-white" id="modalTitle">{{ modalTitle }}</h5>
                    <button type="button" class="close" aria-label="Close" v-on:click="$emit('cancelEvent', $event)">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <slot></slot>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success" v-on:click="$emit('successEvent', $event)">{{ successBtnText }}</button>
                    <button type="button" class="btn btn-secondary" v-on:click="$emit('cancelEvent', $event)">{{ cancelBtnText }}</button>
                </div>
            </div>
        </div>
    </div>
    `,

    props: {
        id: String,
        modalTitle: String,
        successBtnText: String,
        cancelBtnText: String,
        headerClass: {
            type: String,
            default: ""
        }
    },

    data: function() {
        return {};
    },

    methods: {},

    mounted() {},

    destroyed() {}
});
