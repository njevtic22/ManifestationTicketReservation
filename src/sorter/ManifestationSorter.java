package sorter;

import model.Manifestation;

import java.util.Comparator;
import java.util.List;

public class ManifestationSorter {
    public void sortByDate(List<Manifestation> manifestations, int order) {
        Comparator<Manifestation> manifestationComparator = new Comparator<Manifestation>() {
            @Override
            public int compare(Manifestation o1, Manifestation o2) {
                return order * o1.getHoldingDate().compareTo(o2.getHoldingDate());
            }
        };

        manifestations.sort(manifestationComparator);
    }
}
