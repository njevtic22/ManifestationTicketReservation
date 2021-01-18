package useCase.manifestation.dto;

import model.Manifestation;

public class GetAllManifestationsDTO {
    public long id;
    public String name;
    public long numberOfTicketsLeft;
    public double regularTicketPrice;
    public String holdingDate;
    public String status;
    public String type;

    //    public String street;
//    public long number;
//    public String city;
//    public String postalCode;
    public GetLocationForAllManifestationsDTO location;

    public String imageLocation;

    public GetAllManifestationsDTO(Manifestation manifestation, String parsedDate) {
        this.id = manifestation.getId();
        this.name = manifestation.getName();
        this.numberOfTicketsLeft = manifestation.getNumberOfTicketsLeft();
        this.regularTicketPrice = manifestation.getRegularTicketPrice();
        this.holdingDate = parsedDate;
        this.status = manifestation.getStatus().toString();
        this.type = manifestation.getType().toString();
        this.location = new GetLocationForAllManifestationsDTO(manifestation.getLocation());
        this.imageLocation = manifestation.getImage().getLocation();
    }
}
