package useCase.manifestation.dto;

import model.Manifestation;
import model.Review;
import model.ReviewStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    public boolean hasEnded;
    public boolean isSoldOut;

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
        this.maxNumberOfTickets = manifestation.getMaxNumberOfTickets();
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
        if (!manifestation.getReviews().isEmpty()) {
            int reviewToDivide = 0;
            for (Review review : manifestation.getReviews()) {
                if (review.getStatus() == ReviewStatus.APPROVED && !review.isArchived()) {
                    avgRating += review.getRating();
                    reviewToDivide++;
                }
            }
            if (avgRating > 0)
                this.avgRating = this.avgRating / reviewToDivide;
        }



        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(manifestation.getHoldingDate());
        calendar.add(Calendar.HOUR, 2);

        Date endDate = calendar.getTime();
        Date currentDate = new Date();

        this.hasEnded = endDate.before(currentDate);


        this.numberOfTicketsLeft = manifestation.getNumberOfTicketsLeft();
        this.isSoldOut = manifestation.isSoldOut();
    }

    public String imageLocationToBase64(String imageLocation) {
        try {
            File file = new File(imageLocation);
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
