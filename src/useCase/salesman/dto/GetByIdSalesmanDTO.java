package useCase.salesman.dto;

import model.Salesman;

public class GetByIdSalesmanDTO {
    public long id;
    public String name;
    public String surname;
    public String username;
    public String dateOfBirth;
    public String gender;

    public GetByIdSalesmanDTO(Salesman salesman, String parsedDate) {
        this.id = salesman.getId();
        this.name = salesman.getName();
        this.surname = salesman.getSurname();
        this.username = salesman.getUsername();
        this.dateOfBirth = parsedDate;
        this.gender = salesman.getGender().toString();
    }
}
