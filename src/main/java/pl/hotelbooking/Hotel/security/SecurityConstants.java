package pl.hotelbooking.Hotel.security;

public interface SecurityConstants {
    Long EXPIRATION_TIME = 432000000L;
    String TOKEN_PREFIX = "Bearer ";
    String CLAIM = "Role";
    String HEADER_TOKEN = "Authorization";
    String SECRET_KEY = "veryVerySecretKey";
}
