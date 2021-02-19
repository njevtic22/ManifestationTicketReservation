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
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar3" viewBox="0 0 16 16">
                            <path d="M14 0H2a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM1 3.857C1 3.384 1.448 3 2 3h12c.552 0 1 .384 1 .857v10.286c0 .473-.448.857-1 .857H2c-.552 0-1-.384-1-.857V3.857z"/>
                            <path d="M6.5 7a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                        </svg>
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
