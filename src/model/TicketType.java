package model;

public enum TicketType {
    VIP(4),
    FAN_PIT(2),
    REGULAR(1);

    private int multiplyBy;

    TicketType(int i) {
        this.multiplyBy = i;
    }

    public int getMultiplyBy() {
        return multiplyBy;
    }
}
