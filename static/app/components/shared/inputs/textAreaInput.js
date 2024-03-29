Vue.component("textAreaInput", {
    template: `
        <div>
            <label v-if="labelText" v-bind:for="name">
                {{ labelText }}
            </label>
            <div class="input-group">
                <textarea
                    type="text"
                    class="form-control"
                    v-bind:class="{'is-invalid': isInvalid}"
                    v-bind:name="name"
                    v-bind:value="value"
                    v-bind:required="required"
                    v-bind:disabled="disabled"
                    v-bind:placeholder="placeholder"
                    v-bind:style="{height: height}"
                    v-bind:minlength="minLength"

                    v-on:input="$emit('input', $event.target.value)"
                ></textarea>
                <div class="invalid-tooltip">
                    {{ errorMessage }}
                </div>
            </div>

            <!--
                class="scroll scroll-invisible " to hide scrollbar and also prevent resizing
                style="resize:none" to prevent resizing
            -->
        </div>
    `,

    props: {
        height: String,
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
        minLength: {
            type: Number,
            default: 100
        }
    },

    mounted() { }
});
