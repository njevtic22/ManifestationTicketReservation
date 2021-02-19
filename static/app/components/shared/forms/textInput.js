Vue.component("textInput", {
    template: `
        <div v-bind:class="[componentClass]">
            <label v-if="showLabel" v-bind:for="name">
                <slot></slot>
            </label>
            <div class="input-group">
                <input
                    class="form-control"
                    v-bind:class="{'is-invalid': isInvalid}"
                    type="text"
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
        showLabel: {
            type: Boolean,
            default: false
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
