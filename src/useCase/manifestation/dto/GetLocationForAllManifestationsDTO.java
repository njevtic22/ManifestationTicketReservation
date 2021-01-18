package useCase.manifestation.dto;

import model.Address;
import model.Location;

public class GetLocationForAllManifestationsDTO {
    public long id;
    public double longitude;
    public double latitude;

    public String formattedAddress;

    public GetLocationForAllManifestationsDTO(Location location) {
        this.id = location.getId();
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.formattedAddress = formatAddress(location.getAddress());
    }

    private String formatAddress(Address address) {
        return address.getStreet() + " " + address.getNumber() + ", " + address.getCity() + ", " + address.getPostalCode();
    }
}
