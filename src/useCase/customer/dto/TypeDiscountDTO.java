package useCase.customer.dto;

public class TypeDiscountDTO {
    public String type;
    public float discount;

    public TypeDiscountDTO(String type, float discount) {
        this.type = type;
        this.discount = discount;
    }
}
