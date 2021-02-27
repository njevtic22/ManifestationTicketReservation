package useCase.user.dto;

import model.Customer;
import model.User;

public class GetAllUsersDTO {
    public long id;
    public String name;
    public String surname;
    public String username;
    public String dateOfBirth;
    public String gender;
    public String role;
    public String type;
    public double points;

    public GetAllUsersDTO(User user, String parsedDate) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.username = user.getUsername();
        this.dateOfBirth = parsedDate;
        this.gender = user.getGender().toString();
        this.role = user.getClass().getSimpleName().toUpperCase();

        if (user instanceof Customer) {
            type = ((Customer) user).getType().toString();
            points = ((Customer) user).getPoints();
        } else {
            type = "/";
            points = 0;
        }
    }
}
