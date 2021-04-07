Vue.component("fileInput", {
    template: `
        <div>
            <label v-if="labelText" v-bind:for="name">
                {{ labelText }}
            </label>
            <div class="input-group">
                <input
                    type="file"
                    class="form-control"
                    v-bind:class="{'is-invalid': isInvalid}"
                    v-bind:name="name"
                    v-bind:value="value"
                    v-bind:required="required"
                    v-bind:disabled="disabled"
                    v-bind:placeholder="placeholder"
                    
                    v-on:input="$emit('input', $event.target.value)"
                >
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
        labelText: {
            type: String,
            default: ""
        },
        isInvalid: {
            type: Boolean,
            default: false
        },
        placeholder: {
            type: String,
            default: ""
        },
        required: {
            type: Boolean,
            default: false
        },
        disabled: {
            type: Boolean,
            default: false
        }
    },

    mounted() { }
});
