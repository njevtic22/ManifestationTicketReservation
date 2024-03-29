package useCase.customer.dto;

import model.Customer;

public class GetByIdCustomerDTO {
    public long id;
    public String name;
    public String surname;
    public String username;
    public String dateOfBirth;
    public String gender;
    public String role;

    public double points;
    public String type;

    public GetByIdCustomerDTO(Customer customer, String parsedDate) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.surname = customer.getSurname();
        this.username = customer.getUsername();
        this.dateOfBirth = parsedDate;
        this.gender = customer.getGender().toString();
        this.role = customer.getClass().getSimpleName().toUpperCase();
        this.points = customer.getPoints();
        this.type = customer.getType().toString();
    }
}
