Vue.component("textInput", {
    template: `
        <div v-bind:class="[componentClass]">
            <label v-if="labelText" v-bind:for="name">
                {{ labelText }}
            </label>
            <div class="input-group">
                <input
                    type="text"
                    class="form-control"
                    v-bind:class="{'is-invalid': isInvalid}"
                    :name="name"
                    :value="value"
                    :required="required"
                    :disabled="disabled"
                    :placeholder="placeholder"
                    v-on:input="$emit('input', $event.target.value)"
                >
                <div class="invalid-tooltip">
                    {{errorMessage}}
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
        },
        componentClass: {
            type: String,
            default: ""
        }
    },

    mounted() { }
});
