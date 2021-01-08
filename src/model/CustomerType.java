package model;

public enum CustomerType {
    BRONZE(2000, 10),
    SILVER(3000, 20),
    GOLD(4000, 30);

    private int minPoints;
    private int discount;

    CustomerType(int minPoints, int discount) {
        this.minPoints = minPoints;
        this.discount = discount;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public int getDiscount() {
        return discount;
    }
}
