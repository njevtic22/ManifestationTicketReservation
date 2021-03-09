Vue.component("manifestationCards", {
    template: `
    <div v-bind:class="[cardsClass]">
        <!-- <button type="button" v-on:click="showScreenSize">{{ JSON.stringify(window) }}</button> -->
        <div class="col mb-4" v-for="man in manifestations">
            <manifestationCard
                class="h-100 shadow-lg"
                v-bind:manifestation="man"
            >
            </manifestationCard>
        </div>
    </div>
    `,

    props: {
        manifestations: Array
    },

    data: function() {
        return {
            window: {
                width: 0,
                height: 0
            }
        };
    },

    computed: {
        cardsClass: function() {
            let cardsPerRow = 6;
            if (this.window.width <= 3000)
                cardsPerRow = 5;
            if (this.window.width <= 2500)
                cardsPerRow = 4;
            if (this.window.width <= 2000)
                cardsPerRow = 3;
            if (this.window.width <= 1500)
                cardsPerRow = 2;
            if (this.window.width <= 1000)
                cardsPerRow = 1;
            return `row row-cols-1 row-cols-md-${cardsPerRow}`;
        }
    },

    methods: {
        handleResize: function() {
            this.window.width = window.innerWidth;
            this.window.height = window.innerHeight;
        },

        showScreenSize: function() {
            alert("this.window.width: " + this.window.width + "\n"
                + "this.window.height: " + this.window.height);
        }
    },

    mounted() {
        window.addEventListener('resize', this.handleResize);
        this.handleResize();
    },

    destroyed() {
        window.removeEventListener('resize', this.handleResize);
    }
});
