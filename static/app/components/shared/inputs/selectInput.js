Vue.component('vue-multiselect', window.VueMultiselect.default);

Vue.component('selectInput',{
    template: `
        <div>   
            <label v-if="labelText" v-bind:for="name">
                {{ labelText }}
            </label>  
            <div class="input-group">
                <vue-multiselect
                    v-bind:name="name"
                    v-bind:value="value"
                    v-bind:options="options"
                    v-bind:searchable="searchable"
                    v-bind:show-labels="showLabels"
                    v-bind:close-on-select="closeOnSelect"
                    v-bind:disabled="disabled"
                    v-bind:allow-empty="!required"
                    v-bind:preselect-first="required"
                    v-bind:placeholder="placeholder"
                    
                    v-on:select="$emit('select', $event)"
                >
                </vue-multiselect>
                <div class="invalid-tooltip">
                    {{ errorMessage }}
                </div>
            </div>
        </div>
    `,

    props: {
        name: String,
        value: String,
        errorMessage: String,
        searchable: {
            type: Boolean,
            default: false
        },
        showLabels: {
            type: Boolean,
            default: false
        },
        closeOnSelect: {
            type: Boolean,
            default: true
        },
        placeholder: {
            type: String,
            default: ""
        },
        labelText: {
            type: String,
            default: ""
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
        }
    }
});
