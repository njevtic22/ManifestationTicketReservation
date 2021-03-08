package useCase.manifestation.dto;

import jdk.jfr.Frequency;
import model.Manifestation;
import model.Review;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class GetAllManifestationsDTO {
    public long id;
    public String name;
    public long numberOfTicketsLeft;
    public long maxNumberOfTickets;
    public double regularTicketPrice;
    public String holdingDate;
    public String description;
    public String status;
    public String type;
    public double avgRating;

    //    public String street;
//    public long number;
//    public String city;
//    public String postalCode;
    public GetLocationForAllManifestationsDTO location;

    public String imageBase64;
    public String imageType;

    public GetAllManifestationsDTO(Manifestation manifestation, String parsedDate) {
        this.id = manifestation.getId();
        this.name = manifestation.getName();
        this.numberOfTicketsLeft = manifestation.getNumberOfTicketsLeft();
        this.maxNumberOfTickets = manifestation.getTickets().size();
        this.regularTicketPrice = manifestation.getRegularTicketPrice();
        this.holdingDate = parsedDate;
        this.description = manifestation.getDescription();
        this.status = manifestation.getStatus().toString();
        this.type = manifestation.getType().toString();
        this.location = new GetLocationForAllManifestationsDTO(manifestation.getLocation());

        String imageLocation = manifestation.getImage().getLocation();
        this.imageBase64 = imageLocationToBase64(imageLocation);
        int dotIndex = imageLocation.lastIndexOf(".");
        this.imageType = imageLocation.substring(dotIndex + 1);

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
