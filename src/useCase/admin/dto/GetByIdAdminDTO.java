package useCase.admin.dto;

import model.Admin;

public class GetByIdAdminDTO {
    public long id;
    public String name;
    public String surname;
    public String username;
    public String dateOfBirth;
    public String gender;

    public GetByIdAdminDTO(Admin admin, String parsedDate) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.surname = admin.getSurname();
        this.username = admin.getUsername();
        this.dateOfBirth = parsedDate;
        this.gender = admin.getGender().toString();
    }
}
