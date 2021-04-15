package useCase.authentication.dto;

public class TokenResponse {
    public String token;
    public String role;

    public String customerType;
    public float customerDiscount;

    public TokenResponse(String token, String role, String customerType, float customerDiscount) {
        this.token = token;
        this.role = role;
        this.customerType = customerType;
        this.customerDiscount = customerDiscount;
    }
}
