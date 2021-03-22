Vue.component("baseForm", {
    template: `
    <form
        v-bind:id="id"
        novalidate
    >
        <slot></slot>
    </form>
    `,
    props: {
        id: String
    },

    methods: {
        validateForm: function() {
            let form = $(`#${this.id}`);
            form.addClass("was-validated");
            return form[0].checkValidity();
        },

        removeValidation: function() {
            let form = $(`#${this.id}`);
            form.removeClass("was-validated");
        }
    },

    mounted() {},

    destroyed() {}
});
