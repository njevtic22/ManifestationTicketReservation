package useCase.manifestation.dto;

import model.Manifestation;
import model.TicketStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class GetByIdManifestationDTO {
    public long id;
    public String name;
    public long numberOfTicketsLeft;

    public long numberOfRegularTicketsLeft;
    public long numberOfFanTicketsLeft;
    public long numberOfVipTicketsLeft;

    public long maxNumberOfTickets;

    public double regularTicketPrice;
    public double fanTicketPrice;
    public double vipTicketPrice;

    public String holdingDate;
    public String description;
    public String status;
    public String type;
    public double avgRating;
    public boolean hasEnded;
    public boolean isSoldOut;

    public GetLocationForManifestationDTO location;

//    public String formattedAddress;

    public String imageBase64;
    public String imageType;

//    public List<GetAllReviewsForManifestationDTO> reviews;

    public GetByIdManifestationDTO(Manifestation manifestation, String parsedDate) {
        this.id = manifestation.getId();
        this.name = manifestation.getName();
        this.maxNumberOfTickets = manifestation.getMaxNumberOfTickets();

        this.regularTicketPrice = manifestation.getRegularTicketPrice();
        this.fanTicketPrice = manifestation.getFanTicketPrice();
        this.vipTicketPrice = manifestation.getVipTicketPrice();

        this.holdingDate = parsedDate;
        this.description = manifestation.getDescription();
        this.status = manifestation.getStatus().toString();
        this.type = manifestation.getType().toString();

        this.location = new GetLocationForManifestationDTO(manifestation.getLocation());


        String imageLocation = manifestation.getImage().getLocation();
        this.imageBase64 = imageLocationToBase64(imageLocation);
        int dotIndex = imageLocation.lastIndexOf(".");
        this.imageType = imageLocation.substring(dotIndex + 1);

//        this.reviews = manifestation.getReviews()
//                .stream()
//                // filtering out not approved reviews and deleted reviews
//                .filter(review -> /*review.getStatus() == ReviewStatus.APPROVED ||*/ !review.isArchived())
//                .map(GetAllReviewsForManifestationDTO::new)
//                .collect(Collectors.toList());


        this.avgRating = 0;
        if (!manifestation.getReviews().isEmpty()) {
            manifestation.getReviews().forEach(review -> avgRating += review.getRating());
            this.avgRating = this.avgRating / manifestation.getReviews().size();
        }


        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(manifestation.getHoldingDate());
        calendar.add(Calendar.HOUR, 2);

        Date endDate = calendar.getTime();
        Date currentDate = new Date();

        this.hasEnded = endDate.before(currentDate);


        this.numberOfTicketsLeft = manifestation.getNumberOfTicketsLeft();
        this.isSoldOut = manifestation.isSoldOut();


        this.numberOfRegularTicketsLeft = manifestation.getNumberOfRegularTicketsLeft();
        this.numberOfFanTicketsLeft = manifestation.getNumberOfFanTicketsLeft();
        this.numberOfVipTicketsLeft = manifestation.getNumberOfVipTicketsLeft();
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
