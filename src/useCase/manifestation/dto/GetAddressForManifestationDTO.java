package useCase.manifestation.dto;

import model.Address;

public class GetAddressForManifestationDTO {
    public String street;
    public long number;
    public String city;
    public String postalCode;

    public GetAddressForManifestationDTO(Address address) {
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
    }
}
