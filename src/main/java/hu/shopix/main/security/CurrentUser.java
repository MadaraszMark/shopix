package hu.shopix.main.security;

import hu.shopix.main.model.User;
import hu.shopix.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component // Spring bean
@RequiredArgsConstructor // Lombok -> automatikus konstruktor
public class CurrentUser {

    private final UserRepository userRepository;

    // Visszaadja a bejelentkezett felhasználó ID-ját
    public Long requireId(String email) {
        return requireEntity(email).getId();
    }

    // Betölti a User entitást az email alapján.
    public User requireEntity(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nincs bejelentkezve.");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Felhasználó nem található."));
    }

    public void ensureSameUser(Long pathUserId, String email) {
        Long authUserId = requireId(email);
        if (!authUserId.equals(pathUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Más felhasználó erőforrása.");
        }
    }
}
