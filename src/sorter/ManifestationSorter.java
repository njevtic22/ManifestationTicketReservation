package sorter;

import model.Address;
import model.Location;
import model.Manifestation;

import java.util.Comparator;
import java.util.List;

public class ManifestationSorter {
    public void sortByName(List<Manifestation> manifestations, int order) {
        Comparator<Manifestation> manifestationComparator = new Comparator<Manifestation>() {
            @Override
            public int compare(Manifestation o1, Manifestation o2) {
                return order * o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        };

        manifestations.sort(manifestationComparator);
    }

    public void sortByDate(List<Manifestation> manifestations, int order) {
        Comparator<Manifestation> manifestationComparator = new Comparator<Manifestation>() {
            @Override
            public int compare(Manifestation o1, Manifestation o2) {
                return order * o1.getHoldingDate().compareTo(o2.getHoldingDate());
            }
        };

        manifestations.sort(manifestationComparator);
    }

    public void sortByPrice(List<Manifestation> manifestations, int order) {
        Comparator<Manifestation> manifestationComparator = new Comparator<Manifestation>() {
            @Override
            public int compare(Manifestation o1, Manifestation o2) {
                return order * Double.compare(o1.getRegularTicketPrice(), o2.getRegularTicketPrice());
            }
        };

        manifestations.sort(manifestationComparator);
    }

    /*
    *
    ○ Lokacija,
    *
    * */

    public void sortByLocation(List<Manifestation> manifestations, int order) {
        Comparator<Manifestation> manifestationComparator = new Comparator<Manifestation>() {
            @Override
            public int compare(Manifestation o1, Manifestation o2) {
                Location firstLocation = o1.getLocation();
                Location secondLocation = o2.getLocation();

                Address firstAddress = firstLocation.getAddress();
                Address secondAddress = secondLocation.getAddress();

                String firstFormattedAddress = firstAddress.toString();
                String secondFormattedAddress = secondAddress.toString();

                return order * firstFormattedAddress.compareTo(secondFormattedAddress);
            }
        };

        manifestations.sort(manifestationComparator);
    }
}
