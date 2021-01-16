package useCase.authentication.dto;

public class TokenResponse {
    public String token;
    public String role;

    public TokenResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}
