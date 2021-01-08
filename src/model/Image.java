package model;

import utility.generator.LongGenerator;

import java.util.Objects;

public class Image {
    private Long id;
    private String location;

    private static LongGenerator idGenerator;

    public Image(String location) {
        this(idGenerator.next(), location);
    }

    public Image(Long id, String location) {
        this.id = id;
        this.location = location;
    }

    public static void initGenerator() {
        Image.initGenerator(0L);
    }

    public static void initGenerator(Long start) {
        Image.idGenerator = new LongGenerator(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return id.equals(image.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public byte[] toBytes() {
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
