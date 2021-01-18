package security;

public class NoOpPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null)
            return false;
        if (encodedPassword == null)
            return false;
        return rawPassword.equals(encodedPassword);
    }
}
