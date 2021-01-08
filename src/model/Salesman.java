package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Salesman extends User {
    private List<Manifestation> manifestations;

    public Salesman(String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived, List<Manifestation> manifestations) {
        super(
                name,
                surname,
                username,
                password,
                dateOfBirth,
                gender,
                archived
        );
        this.manifestations = manifestations;
    }

    public Salesman(Long id, String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived, List<Manifestation> manifestations) {
        super(id, name, surname, username, password, dateOfBirth, gender, archived);
        this.manifestations = manifestations;
    }

    public Salesman(String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived) {
        super(name, surname, username, password, dateOfBirth, gender, archived);
        this.manifestations = new ArrayList<>();
    }
    public Salesman(Long id, String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived) {
        super(id, name, surname, username, password, dateOfBirth, gender, archived);
        this.manifestations = new ArrayList<>();
    }
}
