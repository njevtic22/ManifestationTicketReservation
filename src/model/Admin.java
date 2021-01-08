package model;

import java.util.Date;

public class Admin extends User {
    public Admin(String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived) {
        super(name, surname, username, password, dateOfBirth, gender, archived);
    }

    public Admin(Long id, String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived) {
        super(id, name, surname, username, password, dateOfBirth, gender, archived);
    }
}
