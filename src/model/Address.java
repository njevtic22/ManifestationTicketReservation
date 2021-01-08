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
        return id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
