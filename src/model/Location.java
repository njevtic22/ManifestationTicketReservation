package model;

import utility.generator.LongGenerator;

import java.util.Objects;

public class Location {
    private Long id;
    private double longitude;
    private double latitude;

    private Address address;

    private static LongGenerator idGenerator;

    public Location(double longitude, double latitude, Address address) {
        this(idGenerator.next(), longitude, latitude, address);
    }

    public Location(Long id, double longitude, double latitude, Address address) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public static void initGenerator() {
        Location.initGenerator(0L);
    }

    public static void initGenerator(Long start) {
        Location.idGenerator = new LongGenerator(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return id.equals(location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
