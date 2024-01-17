package pl.szupke.onlineShop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.szupke.onlineShop.security.model.ShopUserDetails;
import pl.szupke.onlineShop.security.model.User;
import pl.szupke.onlineShop.security.model.UserRole;
import pl.szupke.onlineShop.security.repository.UserRepository;

import java.util.Date;
import java.util.List;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private long expirationTime;
    private String secret;

    public LoginController(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           @Value("${jwt.expirationTime}") long expirationTime,
                           @Value("${jwt.secret}") String secret) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredential loginCredential) {
        return authenticate(loginCredential.getUsername(), loginCredential.getPassword());
    }



    @PostMapping("/register")
    public Token register(@RequestBody @Valid RegisterCredential registerCredential) {
        if (!registerCredential.getPassword().equals(registerCredential.getRepeatPassword())){
            throw new IllegalArgumentException("Hasła nie są identyczne");
        }
        if (userRepository.existsByUsername(registerCredential.getUsername())){
            throw new IllegalArgumentException("Taki użytkownik istnieje już w bazie danych");
        }
        userRepository.save(User.builder()
                        .username(registerCredential.getUsername())
                        .password("{bcrypt}" + new BCryptPasswordEncoder().encode(registerCredential.getPassword()))
                        .enabled(true)
                        .authorities(List.of(UserRole.ROLE_CUSTOMER))
                .build());
        return authenticate(registerCredential.getUsername(), registerCredential.getPassword());
    }

    private Token authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getId(), password));

        ShopUserDetails principal = (ShopUserDetails) authenticate.getPrincipal();

        String token = JWT.create()
                .withSubject(String.valueOf(principal.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
        return new Token(token, principal.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .filter(s -> UserRole.ROLE_ADMIN.name().equals(s))
                .map(s -> true)
                .findFirst()
                .orElse(false));
    }

    @Getter
    private static class LoginCredential {
        private String username;
        private String password;
    }

    @Getter
    private static class RegisterCredential {

        @Email
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String repeatPassword;
    }

    @Getter
    @AllArgsConstructor
    private static class Token {
        private String token;
        private boolean adminAcces;
    }
}
