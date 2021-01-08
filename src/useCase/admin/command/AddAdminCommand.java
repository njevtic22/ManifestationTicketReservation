package useCase.admin.command;

public class AddAdminCommand {
    public long id;
    public String name;
    public String surname;
    public String username;
    public String password;
    public String dateOfBirth;
    public String gender;

    public AddAdminCommand(long id, String name, String surname, String username, String password, String dateOfBirth, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }
}
