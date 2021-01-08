package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends User {
    private double points;
    private CustomerType type;
    private List<Ticket> tickets;
    private List<WithdrawalHistory> withdrawalHistory;

    public Customer(String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived, double points, CustomerType type, List<Ticket> tickets, List<WithdrawalHistory> withdrawalHistory) {
        super(
                name,
                surname,
                username,
                password,
                dateOfBirth,
                gender,
                archived
        );
        this.points = points;
        this.type = type;
        this.tickets = tickets;
        this.withdrawalHistory = withdrawalHistory;
    }

    public Customer(Long id, String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived, double points, CustomerType type, List<Ticket> tickets, List<WithdrawalHistory> withdrawalHistory) {
        super(id, name, surname, username, password, dateOfBirth, gender, archived);
        this.points = points;
        this.type = type;
        this.tickets = tickets;
        this.withdrawalHistory = withdrawalHistory;
    }

    public Customer(String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived, double points, CustomerType type) {
        super(
                name,
                surname,
                username,
                password,
                dateOfBirth,
                gender,
                archived
        );
        this.points = points;
        this.type = type;
        this.tickets = new ArrayList<>();
        this.withdrawalHistory = new ArrayList<>();
    }

    public Customer(Long id, String name, String surname, String username, String password, Date dateOfBirth, Gender gender, boolean archived, double points, CustomerType type) {
        super(id, name, surname, username, password, dateOfBirth, gender, archived);
        this.points = points;
        this.type = type;
        this.tickets = new ArrayList<>();
        this.withdrawalHistory = new ArrayList<>();
    }
}
