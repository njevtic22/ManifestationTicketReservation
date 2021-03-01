Vue.component('vue-multiselect', window.VueMultiselect.default);

Vue.component('selectInput',{
    template: `
        <div v-bind:class="[componentClass]">   
            <label v-if="showLabel" v-bind:for="name">
               <slot></slot>
            </label>  
            <div class="input-group">
                <vue-multiselect
                    :name="name"
                    :value="value"
                    :options="options"
                    :searchable="false"
                    :show-labels="false"
                    :close-on-select="true"
                    :disabled="disabled"
                    :allow-empty="!required"
                    :preselect-first="required"
                    
                    v-on:select="$emit('select', $event)"
                >
                </vue-multiselect>
                <div class="invalid-tooltip" v-bind:style="{display: display}">
                    {{errorMessage}}
                </div>
            </div>
        </div>
    `,

    data: function() {
        return {
            display: 'none'
        }
    },

    props: {
        name: String,
        value: String,
        errorMessage: String,
        showLabel: {
            type: Boolean,
            default: false
        },
        options: {
            type: Array
        },
        required: {
            type: Boolean,
            default: false
        },
        disabled: {
            type: Boolean,
            default: false
        },
        componentClass: {
            type: String,
            default: ""
        }
    },
    
    methods: {
        validate() {
            this.display = 'block';
        }
    }
});
