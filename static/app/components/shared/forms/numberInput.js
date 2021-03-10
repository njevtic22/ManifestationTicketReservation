Vue.component("numberInput", {
    template: `
        <div v-bind:class="[componentClass]">
            <label v-if="labelText" v-bind:for="name">
                {{ labelText }}
            </label>
            <div class="input-group">
                <input
                    type="number"
                    class="form-control"
                    :min="min"
                    v-bind:class="{'is-invalid': isInvalid}"
                    :name="name"
                    :value="value"
                    :required="required"
                    :disabled="disabled"

                    v-on:input="$emit('input', Number($event.target.value))"
                >
                <div class="invalid-tooltip">
                    {{errorMessage}}
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
        },
        componentClass: {
            type: String,
            default: ""
        }
    },
    
    mounted() { }
});