package security;

/*
* Not used because org.springframework.security.crypto.password.PasswordEncoder
* is used instead
*/
public interface PasswordEncoder {
    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
