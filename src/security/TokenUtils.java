package security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import model.User;
import spark.Request;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// Utility klasa za rad sa JSON Web Tokenima
public class TokenUtils {

    // Izdavac tokena
    private final String APP_NAME = "manifestation-ticket-reservation-program";

    // Algoritam za potpisivanje JWT
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    // Tajna koju samo backend aplikacija treba da zna kako bi mogla da generise i proveri JWT https://jwt.io/
    private final Key SECRET_KEY = Keys.secretKeyFor(SIGNATURE_ALGORITHM);

    // Period vazenja - 5h
    private final int EXPIRES_IN = 18000000;

    // Naziv headera kroz koji ce se prosledjivati JWT u komunikaciji server-klijent
    private final String AUTH_HEADER = "Authorization";

    private final GregorianCalendar calendar = new GregorianCalendar();

    // Moguce je generisati JWT za razlicite klijente (npr. web i mobilni klijenti nece imati isto trajanje JWT, JWT za mobilne klijente ce trajati duze jer se mozda aplikacija redje koristi na taj nacin)
    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";


    // Funkcija za generisanje JWT token
    public String generateToken(Long id) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(id.toString())
                .setAudience(generateAudience())
                .setIssuedAt(calendar.getTime())
                .setExpiration(generateExpirationDate())
                // .claim("key", value) //moguce je postavljanje proizvoljnih podataka u telo JWT tokena
                .signWith(SECRET_KEY, SIGNATURE_ALGORITHM)
                .compact();
    }

    // Funkcija za refresh JWT tokena
    public String refreshToken(String token) {
        final Claims claims = this.getAllClaimsFromToken(token);
        claims.setIssuedAt(calendar.getTime());
        return  Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SECRET_KEY, SIGNATURE_ALGORITHM)
                .compact();
    }

    // Funkcija za citanje svih podataka iz JWT tokena
    private Claims getAllClaimsFromToken(String token) {
        // Throws SignatureException and ExpiredJwtException if token is not valid
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);

        // Exception is not thrown, therefore everything is ok with jwt token
        return jwsClaims.getBody();
    }

    private String generateAudience() {
//		Moze se iskoristiti org.springframework.mobile.device.Device objekat za odredjivanje tipa uredjaja sa kojeg je zahtev stigao.

//		String audience = AUDIENCE_UNKNOWN;
//		if (device.isNormal()) {
//			audience = AUDIENCE_WEB;
//		} else if (device.isTablet()) {
//			audience = AUDIENCE_TABLET;
//		} else if (device.isMobile()) {
//			audience = AUDIENCE_MOBILE;
//		}
        return AUDIENCE_WEB;
    }

    private Date generateExpirationDate() {
        calendar.add(Calendar.MILLISECOND, EXPIRES_IN);
        return calendar.getTime();
    }

    public boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = this.getIssuedAtDateFromToken(token);
        return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
                && (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
    }

    // Funkcija za validaciju JWT tokena
    public Boolean validateToken(String token, User user) {
        final Long id = getIdFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

        return (id != null && id.equals(user.getId())
                /*&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())*/);
    }

    public Long getIdFromToken(String token) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return Long.valueOf(claims.getSubject());
    }

    public Date getIssuedAtDateFromToken(String token) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return claims.getIssuedAt();
    }

    public String getAudienceFromToken(String token) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return claims.getAudience();
    }

    public Date getExpirationDateFromToken(String token) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return claims.getExpiration();
    }

    public int getExpiredIn() {
        return EXPIRES_IN;
    }

    // Funkcija za preuzimanje JWT tokena iz zahteva
    public String getToken(Request request) {
        String authHeader = getAuthHeaderFromHeader(request);

        // JWT se prosledjuje kroz header Authorization u formatu:
        // Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(Request request) {
        return request.headers(AUTH_HEADER);
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(calendar.getTime());
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = this.getAudienceFromToken(token);
        return (audience.equals(AUDIENCE_TABLET) || audience.equals(AUDIENCE_MOBILE));
    }
}
