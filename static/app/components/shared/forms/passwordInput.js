Vue.component("passwordInput", {
    template: `
        <div v-bind:class="[componentClass]">
            <label v-if="labelText" v-bind:for="name">
                {{ labelText }}
            </label>
            <div class="input-group">
                <input
                    v-bind:type="inputType"
                    class="form-control"
                    v-bind:class="{'is-invalid': isInvalid}"
                    v-bind:name="name"
                    v-bind:value="value"
                    v-bind:required="required"
                    v-bind:placeholder="placeholder"
                    v-on:input="$emit('input', $event.target.value)"
                >
                <div class="input-group-append" v-on:click="toggleView">
                    <span class="input-group-text">
                        <eye-fill-icon v-if="inputType === InputTypes.TEXT"></eye-fill-icon>
                        <eye-slash-fill-icon v-else></eye-slash-fill-icon>
                    </span>
                </div>
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
        componentClass: {
            type: String,
            default: ""
        }
    },
    
    data: function() {
        return {
            inputType: "password",
            InputTypes: Object.freeze({
                TEXT: "text",
                PASSWORD: "password"
            }),


        };
    },

    methods: {
        toggleView: function() {
            if (this.inputType === this.InputTypes.PASSWORD)
                this.inputType = this.InputTypes.TEXT;
            else
                this.inputType = this.InputTypes.PASSWORD;
        }
    },

    mounted() {
        this.inputType = this.InputTypes.PASSWORD;
    }
});
