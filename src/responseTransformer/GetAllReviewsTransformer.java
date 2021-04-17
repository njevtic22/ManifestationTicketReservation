package responseTransformer;

import com.google.gson.Gson;
import model.Review;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.review.dto.GetAllReviewsDTO;
import utility.PaginatedResponse;

import java.util.Collection;
import java.util.List;

public class GetAllReviewsTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Review, GetAllReviewsDTO> mapper;

    public GetAllReviewsTransformer(Gson gson, DTOMapper<Review, GetAllReviewsDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        PaginatedResponse<Review> paginatedResponse = (PaginatedResponse<Review>) o;
        List<GetAllReviewsDTO> reviewsDTO = mapper.toDTOList(paginatedResponse.data);

        return gson.toJson(
                new PaginatedResponse<>(
                        reviewsDTO,
                        paginatedResponse.totalNumberOfResults,
                        paginatedResponse.hasPreviousPage,
                        paginatedResponse.hasNextPage
                )
        );
    }
}
