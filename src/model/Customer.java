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

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void addHistory(WithdrawalHistory history) {
        this.withdrawalHistory.add(history);
    }

    public void addPoints(double points) {
        this.points += points;

        if (points >= CustomerType.GOLD.getMinPoints()) {
            this.type = CustomerType.GOLD;
        } else if (points >= CustomerType.SILVER.getMinPoints()) {
            this.type = CustomerType.SILVER;
        } else if (points >= CustomerType.BRONZE.getMinPoints()) {
            this.type = CustomerType.BRONZE;
        }
    }

    public void subtractPoints(double points) {
        this.points -= points;

        if (points >= CustomerType.GOLD.getMinPoints()) {
            this.type = CustomerType.GOLD;
        } else if (points >= CustomerType.SILVER.getMinPoints()) {
            this.type = CustomerType.SILVER;
        } else if (points >= CustomerType.BRONZE.getMinPoints()) {
            this.type = CustomerType.BRONZE;
        }
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<WithdrawalHistory> getWithdrawalHistory() {
        return withdrawalHistory;
    }

    public void setWithdrawalHistory(List<WithdrawalHistory> withdrawalHistory) {
        this.withdrawalHistory = withdrawalHistory;
    }
}
