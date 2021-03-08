package filterSearcher;

import model.Manifestation;
import model.ManifestationType;

import java.util.Collection;
import java.util.Date;

public class ManifestationFilterSearcher {
    public void filterByType(ManifestationType type, Collection<Manifestation> manifestations) {
        manifestations.removeIf(manifestation -> manifestation.getType() != type);
    }

    public void filterByAvailable(Collection<Manifestation> manifestations) {
        manifestations.removeIf(Manifestation::isSoldOut);
    }

    public void searchByName(String name, Collection<Manifestation> manifestations) {
        manifestations.removeIf(manifestation -> !manifestation.getName().toLowerCase().contains(name.toLowerCase()));
    }

    public void searchByDateFrom(Date dateFrom, Collection<Manifestation> manifestations) {
        manifestations.removeIf(manifestation -> manifestation.getHoldingDate().before(dateFrom));
    }

    public void searchByDateTo(Date dateTo, Collection<Manifestation> manifestations) {
        manifestations.removeIf(manifestation -> manifestation.getHoldingDate().after(dateTo));
    }

    public void searchByCity(String city, Collection<Manifestation> manifestations) {
        manifestations.removeIf(manifestation -> !manifestation.getLocation().getAddress().getCity().toLowerCase().contains(city.toLowerCase()));
    }

    public void searchByStreet(String street, Collection<Manifestation> manifestations) {
        manifestations.removeIf(manifestation -> !manifestation.getLocation().getAddress().getStreet().toLowerCase().contains(street.toLowerCase()));
    }

    public void searchByPriceFrom(long priceFrom, Collection<Manifestation> manifestations) {
        manifestations.removeIf(manifestation -> priceFrom > manifestation.getRegularTicketPrice());
    }

    public void searchByPriceTo(long priceTo, Collection<Manifestation> manifestations) {
        manifestations.removeIf(manifestation -> manifestation.getRegularTicketPrice() > priceTo);
    }
}
