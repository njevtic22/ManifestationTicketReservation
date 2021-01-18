package useCase.manifestation.dto;

import model.Location;

public class GetLocationForManifestationDTO {
    public long id;
    public double longitude;
    public double latitude;

    public GetAddressForManifestationDTO address;

    public GetLocationForManifestationDTO(Location location) {
        this.id = location.getId();
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.address = new GetAddressForManifestationDTO(location.getAddress());
    }
}
