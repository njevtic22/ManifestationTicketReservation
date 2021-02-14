Vue.component("passwordInput", {
    template: `
        <div class="form-group">
            <label v-if="showLabel" v-bind:for="name">
                <slot></slot>
            </label>
            <div class="input-group">
                <input
                    class="form-control"
                    v-bind:class="{'is-invalid': isInvalid}"
                    type="password"
                    v-bind:name="name"
                    v-bind:value="value"
                    :placeholder="placeholder"
                    v-on:input="$emit('input', $event.target.value)"
                    v-bind:required="required"
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
        }
    }
});
