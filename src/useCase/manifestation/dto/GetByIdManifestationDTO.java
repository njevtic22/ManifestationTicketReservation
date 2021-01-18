package useCase.manifestation.dto;

import model.Manifestation;

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
                .map(GetAllReviewsForManifestationDTO::new)
                .collect(Collectors.toList());
    }
}
