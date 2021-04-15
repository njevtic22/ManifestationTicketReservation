Vue.component("numberInput", {
    template: `
        <div>
            <label v-if="labelText" v-bind:for="name">
                {{ labelText }}
            </label>
            <div class="input-group">
                <input
                    type="number"
                    class="form-control"
                    v-bind:min="min"
                    v-bind:max="max"
                    v-bind:class="{'is-invalid': isInvalid}"
                    v-bind:name="name"
                    v-bind:value="value"
                    v-bind:required="required"
                    v-bind:disabled="disabled"

                    v-on:input="$emit('input', Number($event.target.value))"
                >
                <div class="invalid-tooltip">
                    {{ errorMessage }}
                </div>
            </div>
        </div>
    `,

    props: {
        name: String,
        value: String | Number,
        min: {
            type: Number,
            default: 0
        },
        max: {
            type: Number,
            default: Infinity
        },
        errorMessage: String,
        labelText: {
            type: String,
            default: ""
        },
        isInvalid: {
            type: Boolean,
            default: false
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