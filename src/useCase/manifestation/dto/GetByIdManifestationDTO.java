package useCase.manifestation.dto;

import model.Manifestation;
import model.ReviewStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
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

    public String imageBase64;
    public String imageType;

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


        String imageLocation = manifestation.getImage().getLocation();
        this.imageBase64 = imageLocationToBase64(imageLocation);
        int dotIndex = imageLocation.lastIndexOf(".");
        this.imageType = imageLocation.substring(dotIndex + 1);

        this.reviews = manifestation.getReviews()
                .stream()
                // filtering out not approved reviews and deleted reviews
                .filter(review -> /*review.getStatus() == ReviewStatus.APPROVED ||*/ !review.isArchived())
                .map(GetAllReviewsForManifestationDTO::new)
                .collect(Collectors.toList());


        this.avgRating = 0;
        manifestation.getReviews().forEach(review -> avgRating += review.getRating());
    }

    public String imageLocationToBase64(String imageLocation) {
        try {
            File file = new File("static" + File.separator + imageLocation);
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
