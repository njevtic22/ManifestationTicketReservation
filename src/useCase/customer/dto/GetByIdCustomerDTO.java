package useCase.customer.dto;

import model.Customer;

public class GetByIdCustomerDTO {
    public long id;
    public String name;
    public String surname;
    public String username;
    public String dateOfBirth;
    public String gender;

    public double points;
    public String type;

    // TODO: Add DTO fro ticket and history

    public GetByIdCustomerDTO(Customer customer, String parsedDate) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.surname = customer.getSurname();
        this.username = customer.getUsername();
        this.dateOfBirth = parsedDate;
        this.gender = customer.getGender().toString();
        this.points = customer.getPoints();
        this.type = customer.getType().toString();
    }
}
