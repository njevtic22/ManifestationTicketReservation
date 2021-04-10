package model;

import utility.generator.LongGenerator;

import java.util.Objects;

public class Address {
    private Long id;
    private String street;
    private long number;
    private String city;
    private String postalCode;

    private static LongGenerator idGenerator;

    public Address(String street, long number, String city, String postalCode) {
        this(idGenerator.next(), street, number, city, postalCode);
    }

    public Address(Long id, String street, long number, String city, String postalCode) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
    }

    public static void initGenerator() {
        Address.initGenerator(0L);
    }

    public static void initGenerator(Long start) {
        Address.idGenerator = new LongGenerator(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return number == address.number &&
                street.equals(address.street) &&
                city.equals(address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, city);
    }

    @Override
    public String toString() {
        return street + " " + number + ", " + city + ", " + postalCode;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
