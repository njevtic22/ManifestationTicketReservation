Vue.component("review", {
    template: `
    <div class="media g-mb-30 media-comment">
        <!-- Review source https://www.bootdey.com/snippets/view/bs4-beta-comment-list -->
        <img class="d-flex g-width-50 g-height-50 rounded-circle g-mt-3 g-mr-15" src=".\\images\\avatar.png" alt="Image Description">
        <div class="media-body u-shadow-v18 g-bg-secondary g-pa-30 shadow">
            <div class="g-mb-15 d-flex justify-content-between">
                <h5 class="h5 g-color-gray-dark-v1 mb-0">{{ review.author }}</h5>
                <h5 class="h5 g-color-gray-dark-v1 mb-0">Grade: {{ review.rating }}</h5>
            </div>
            <br/>
            
            <p
                class="
                    scroll 
                    scroll-invisible 
                    review-description-scroll
                    text-wrap
                    text-break
                    "
            >
                {{ review.comment }}
            </p>

            <div class="d-flex justify-content-end">
                <div class="btn text-white" style="cursor: default;" v-bind:style="{'background-color': getStatusColor}">
                    {{ review.status }}
                </div>
            </div>
        
        </div>
    </div>
    `,

    props: {
        review: {
            type: Object,
            required: true
        }
    },

    data: function() {
        return {
            StatusColors: Object.freeze({
                CREATED: "#0000A0",     // DarkBlue
                REJECTED: "#FF8C00",    // DarkOrange
                APPROVED: "#006400",    // DarkGreen
            }),
        };
    },

    computed: {
        getStatusColor() {
            return this.StatusColors[this.review.status];
        }
    },

    methods: {
        
    },

    mounted() {
    },

    destroyed() {}
});
