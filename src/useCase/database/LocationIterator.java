package useCase.database;

import model.Address;
import model.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LocationIterator implements Iterator<Location> {
    private List<Location> locations = new ArrayList<>();

    public LocationIterator(String fileLocation) {
        this(new File(fileLocation));
    }

    public LocationIterator(File locationsFile) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(locationsFile));
            String line = in.readLine();
            while ((line = in.readLine()) != null) {
                String[] splitLine = line.split("\\|");
                Long id = Long.valueOf(splitLine[0]);
                String[] partsOfAddress = splitLine[1].split(",");
                String[] coordinates = splitLine[2].split(",");


//                System.out.println(line);

                Address address = new Address(
                        id,
                        partsOfAddress[0].strip(),
                        Long.parseLong(partsOfAddress[1].strip()),
                        partsOfAddress[2].strip(),
                        partsOfAddress[3].strip()
                );

                Location location = new Location(
                        id,
                        Double.parseDouble(coordinates[1]),
                        Double.parseDouble(coordinates[0]),
                        address
                );

                locations.add(location);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean hasNext() {
        return locations.size() > 0;
    }

    @Override
    public Location next() throws NoSuchElementException {
        if (!hasNext())
            throw new NoSuchElementException("No more locations.");

        return locations.remove(0);
    }


    public static void main(String[] args) {
        LocationIterator locationIterator = new LocationIterator("databases/manifestationTicketReservation/version1/Locations.csv");

        while (locationIterator.hasNext()) {
            Location location = locationIterator.next();
            System.out.println(location);
            System.out.println("========================================================");
        }
    }
}