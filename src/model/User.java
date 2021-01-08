package model;

import utility.generator.LongGenerator;

import java.util.Date;
import java.util.Objects;

public abstract class User {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private Date dateOfBirth;
    private Gender gender;
    private boolean archived;

    // Initialized in file deserializer
    protected static LongGenerator idGenerator;

    public User(String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived) {
        this(idGenerator.next(), name, surname, username, password, dateOfBirth, gender, archived);
    }

    public User(Long id, String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.archived = archived;
    }

    public static void initGenerator() {
        User.initGenerator(0L);
    }

    public static void initGenerator(Long start) {
        User.idGenerator = new LongGenerator(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
