package useCase.manifestation.dto;

import model.Manifestation;
import model.ReviewStatus;

import java.util.List;
import java.util.stream.Collectors;

public class GetByIdManifestationDTO {
    public long id;
    public String name;
    public long numberOfTicketsLeft;
    public double regularTicketPrice;
    public String holdingDate;
    public String status;
    public String type;
    public double avgRating;

    public GetLocationForManifestationDTO location;

//    public String formattedAddress;

    public String imageLocation;

    public List<GetAllReviewsForManifestationDTO> reviews;

    public GetByIdManifestationDTO(Manifestation manifestation, String parsedDate) {
        this.id = manifestation.getId();
        this.name = manifestation.getName();
        this.numberOfTicketsLeft = manifestation.getNumberOfTicketsLeft();
        this.regularTicketPrice = manifestation.getRegularTicketPrice();
        this.holdingDate = parsedDate;
        this.status = manifestation.getStatus().toString();
        this.type = manifestation.getType().toString();

        this.location = new GetLocationForManifestationDTO(manifestation.getLocation());


        this.imageLocation = manifestation.getImage().getLocation();
        this.reviews = manifestation.getReviews()
                .stream()
                // filtering out not approved reviews and deleted reviews
                .filter(review -> /*review.getStatus() == ReviewStatus.APPROVED ||*/ !review.isArchived())
                .map(GetAllReviewsForManifestationDTO::new)
                .collect(Collectors.toList());


        this.avgRating = 0;
        manifestation.getReviews().forEach(review -> avgRating += review.getRating());
    }
}
