package responseTransformer;

import com.google.gson.Gson;
import model.Review;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.review.dto.GetAllReviewsDTO;

import java.util.Collection;

public class GetAllReviewsTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Review, GetAllReviewsDTO> mapper;

    public GetAllReviewsTransformer(Gson gson, DTOMapper<Review, GetAllReviewsDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Collection<Review> reviews = (Collection<Review>) o;
        return gson.toJson(mapper.toDTOList(reviews));
    }
}
