package model;

public enum CustomerType {
    BRONZE(0, 0.0F),
    SILVER(2500, 0.25F),
    GOLD(5000, 0.50F);

    private int minPoints;
    private float discount;

    CustomerType(int minPoints, float discount) {
        this.minPoints = minPoints;
        this.discount = discount;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public float getDiscount() {
        return discount;
    }
}
