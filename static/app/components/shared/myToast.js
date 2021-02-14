Vue.component("myToast", {
    template: `
    <div
        aria-live="polite"
        aria-atomic="true"
        class="d-flex justify-content-center align-items-center"
    >
        <!-- Then put toasts within -->
        <div
            id="appToast"
            class="toast fade"
            role="alert"
            aria-live="assertive"
            aria-atomic="true"
            data-delay="5000"
            data-animation="true"
        >
            <div class="toast-header bg-success">
                <strong class="mr-auto text-dark">Message</strong>
                <button
                    type="button"
                    class="ml-2 mb-1 close"
                    data-dismiss="toast"
                    aria-label="Close"
                >
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="toast-body">
                {{toastMessage}}
            </div>
        </div>
    </div>
    `,

    data: function() {
        return {
            toastMessage: ""
        };
    },

    methods: {},

    mounted() {
        this.$root.$on("toastSuccess", message => {
            this.toastMessage = message;

            $(".toast-header").removeClass("bg-danger");
            $(".toast-header").addClass("bg-success");
            $("#appToast").toast("show");
        });

        this.$root.$on("toastFailure", message => {
            this.toastMessage = message;

            $(".toast-header").removeClass("bg-success");
            $(".toast-header").addClass("bg-danger");
            $("#appToast").toast("show");
        });
    },

    destroyed() {
        this.$root.$off("toastSuccess");
        this.$root.$off("toastFailure");
    }
});
