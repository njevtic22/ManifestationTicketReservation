Vue.component("classicDateInput", {
    template: `
    <v-date-picker
        value=""
        v-bind:max-date="maxDate"
        v-bind:min-date="minDate"
        v-bind:model-config="dateToStringConfig"
        v-bind:first-day-of-week="2"
        v-bind:masks="masks"
        is24hr 

        v-bind:minute-increment="15" 
        mode="date"

        v-on:input="$emit('input', $event)"
    >
        <template v-slot="{ inputValue, inputEvents }">

        
            <div v-bind:class="[componentClass]">
                <label v-if="showLabel" v-bind:for="name">
                    <slot></slot>
                </label>
                <div class="input-group">
                    <div class="input-group-prepend input-group-text">
                        <calendar3-icon></calendar3-icon>
                    </div>
                        <input
                            class="form-control"
                            v-bind:class="{'is-invalid': isInvalid}"
                            type="text"
                            :name="name"
                            :value="inputValue"
                            :required="required"
                            v-on="inputEvents"
                        >
                    <div class="invalid-tooltip">
                        {{errorMessage}}
                    </div>
                </div>
            </div>

        </template>
    </v-date-picker>
    `,

    
    computed: {
        // converting date to string withouth model-config in v-date-picker
        dateValue: {
          get() {
              return new Date(this.newCustomer.dateOfBirth);
          },
          set(val) {
            this.newCustomer.dateOfBirth = this.dateTimeFormat.format(val);
          },
        },
        //////////////////////////////////////////////////////////////
    },
    
    data: function() {
        return {
            // converting date to string withouth model-config in v-date-picker
            locale: "sr-RS",
            options: { 
                day: "numeric", 
                month: "numeric", 
                year: "numeric", 
                hour: "numeric", 
                minute: "numeric", 
                second: "numeric" 
            },
            dateTimeFormat: Object,

            masks: {
                input: this.$root.getDateFormat()
            },
            ///////////////////////////////////////////////////////////////////


            // for model-config in v-date-picker
            dateToStringConfig: {
                type: 'string',
                mask: this.$root.getDateFormat(), // Uses 'iso' if missing
            },
        };
    },

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
        },
        maxDate: {
            type: Date,
            default: null
        },
        minDate: {
            type: Date,
            default: null
        }
    }, 

    methods: {

    },

    mounted() {
        this.dateTimeFormat = new Intl.DateTimeFormat(this.locale, this.options);
    },

    destroyed() {}
});
