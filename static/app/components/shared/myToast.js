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
            data-animation="true"
            
            v-bind:data-delay="delayTime"
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

    data: function () {
        return {
            toastMessage: "",
            delayTime: 5000,
        };
    },

    methods: {
        removeBgColors: function() {
            $(".toast-header").removeClass("bg-danger");
            $(".toast-header").removeClass("bg-success");
            $(".toast-header").removeClass("bg-primary");
        },

        showSuccess: function(message, timeout) {
            this.toastMessage = message;
            this.delayTime = timeout;

            this.removeBgColors();
            $(".toast-header").addClass("bg-success");
            $("#appToast").toast("show");
        },

        showFailure: function(message, timeout) {
            this.toastMessage = message;
            this.delayTime = timeout;

            this.removeBgColors();
            $(".toast-header").addClass("bg-danger");
            $("#appToast").toast("show");
        },

        showInfo: function(message, timeout) {
            this.toastMessage = message;
            this.delayTime = timeout;

            this.removeBgColors();
            $(".toast-header").addClass("bg-primary");
            $("#appToast").toast("show");
        }
    },

    mounted() {},

    destroyed() {}
});
