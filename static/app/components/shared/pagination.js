Vue.component("pagination", {
    template: `
    <nav aria-label="Page navigation example">
        <ul class="pagination" v-bind:class="[justifyContent]">
            <li class="page-item" v-bind:class="{disabled: !hasPrevious}">
                <button 
                    class="page-link" 
                    aria-label="Previous"
                    v-on:click="navigatePrevious"
                >
                    <span aria-hidden="true">&laquo;</span>
                    <span class="sr-only">Previous</span>
                </button>
            </li>

            
            <div v-for="n in numberOfPrevious">
                <li class="page-item" v-if="hasPrevious && (currentPage - (numberOfPrevious - n + 1)) >= 0">
                    <button 
                        class="page-link"
                        v-on:click="navigateTo(currentPage - (numberOfPrevious - n + 1))" 
                    >
                        {{ currentPage - (numberOfPrevious - n + 1) }}
                    </button>
                </li>
            </div>


            <li class="page-item active">
                <button class="page-link">{{ currentPage }}</button>
            </li>


            <li class="page-item" v-if="hasNext" v-for="n in numberOfPrevious">
                <button 
                    class="page-link"
                    v-on:click="navigateTo(currentPage + n)" 
                >
                    {{ currentPage + n }}
                </button>
            </li>

            <li class="page-item" v-bind:class="{disabled: !hasNext}">
                <button 
                    class="page-link" 
                    aria-label="Next"
                    v-on:click="navigateNext"
                >
                    <span aria-hidden="true">&raquo;</span>
                    <span class="sr-only">Next</span>
                </button>
            </li>
        </ul>
    </nav>
    `,
    props: {
        currentPage: Number,
        hasPrevious: Boolean,
        hasNext: Boolean,

        numberOfPrevious: Number,
        numberOfNext: Number,

        justifyContent: {
            type: String,
            default: ""
        }
    },

    data: function() {
        return {
            arrayOfPrevious: []
        };
    },

    methods: {
        navigatePrevious: function() {
            this.$emit("previous");
        },

        navigateNext: function() {
            this.$emit("next");
        },

        navigateTo: function(to) {
            this.$emit("to", to);
        }
    },

    mounted() {
    },

    destroyed() {}
});
