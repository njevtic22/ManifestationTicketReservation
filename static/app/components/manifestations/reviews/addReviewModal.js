Vue.component("addReviewModal", {
    template: `
    <baseModal
        v-bind:id="id"
        headerClass="bg-success"
        btnSuccessClass="btn-success"
        modalTitle="Add review"
        modalClass=""
        successBtnText="Add review"
        cancelBtnText="Cancel"

        v-on:successEvent="addReview"
        v-on:cancelEvent="cancel"
    >
        <baseForm
            id="addReviewForm"
            ref="addReviewForm"
        >
            <div class="text-center">
                <label for="ratingInput">Rating</label>
                <br/>
                <star-fill-icon 
                    v-for="i in numOfFillStars" 
                    v-bind:key="'fill' + i"
                    style="width: 10%; height: 10%"
                    class="text-primary"
                >
                </star-fill-icon>
                <star-icon 
                    v-for="i in numOfEmptyStars" 
                    v-bind:key="'empty' + i"
                    style="width: 10%; height: 10%"
                    class="text-primary"
                >
                </star-icon>
                <br/>
                <br/>
                <input
                    type="range" 
                    id="ratingInput"
                    class="form-control-range"
                    v-model="reviewToAdd.rating"
                    style="width: 50%; margin-left: 25%;"
                    min="1"
                    max="5"
                >

                <br/>
                <textAreaInput
                    name="comment"
                    placeholder="Your comment goes here"
                    class="form-group w-100 h-100"
                    height="200px"
                    v-model="reviewToAdd.comment"
                    v-bind:minLength="1"
                    v-bind:errorMessage="commentErrorMessage"
                    v-bind:isInvalid="isCommentInvalid"
                    required
                >
                </textAreaInput>
            </div>
        </baseForm>
    </baseModal>
    `,

    props: {
        id: String,
    },

    data: function() {
        return {
            reviewToAdd: {
                rating: 3,
                comment: ""
            },

            commentErrorMessage: "Comment must not be empty",
            isCommentInvalid: false
        };
    },

    computed: {
        numOfFillStars() {
            return Number(this.reviewToAdd.rating);
        },
        
        numOfEmptyStars() {
            return 5 - Number(this.reviewToAdd.rating);
        }
    },

    methods: {
        showInvalidCommentError: function(message) {
            this.commentErrorMessage = message;
            this.isCommentInvalid = true;
        },

        removeInvalidCommentError: function() {
            this.commentErrorMessage = "Name must not be empty";
            this.isCommentInvalid = false;
        },



        removeValidation: function() {
            // var form = $("#addReviewForm");
            // form.removeClass("was-validated");

            this.$refs.addReviewForm.removeValidation();

            this.removeInvalidCommentError();
        },

        validateForm: function() {
            // var form = $("#addReviewForm");
            // // var form = document.getElementById("addReviewForm");

            // form.addClass("was-validated");
            // // form.classList.add("was-validated");
            // return form[0].checkValidity();

            return this.$refs.addReviewForm.validateForm();
        },

        addReview: function() {
            if (this.validateForm()) {
                this.removeValidation();
                this.$emit("addReview", {
                    rating: Number(this.reviewToAdd.rating),
                    comment: this.reviewToAdd.comment
                });
                this.closeModal();
            }
        },

        cancel: function() {
            this.closeModal();
        },

        closeModal: function() {
            $("#" + this.id).modal("hide");
            this.clearModal();
        },

        clearModal: function() {
            this.reviewToAdd = {
                rating: 3,
                comment: ""
            };
        }
    },

    mounted() {},

    destroyed() {}
});
