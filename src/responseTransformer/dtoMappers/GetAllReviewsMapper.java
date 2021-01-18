package responseTransformer.dtoMappers;

import model.Review;
import useCase.review.dto.GetAllReviewsDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllReviewsMapper implements DTOMapper<Review, GetAllReviewsDTO> {
    @Override
    public GetAllReviewsDTO toDTO(Review review) {
        return new GetAllReviewsDTO(review);
    }

    @Override
    public List<GetAllReviewsDTO> toDTOList(Collection<Review> reviews) {
        return reviews
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
