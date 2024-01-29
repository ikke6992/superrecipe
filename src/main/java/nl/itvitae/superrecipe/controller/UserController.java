package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.User;
import nl.itvitae.superrecipe.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("login")
    public Optional<User> login(@RequestBody User user) {
        // 1 haal gebruikersdata op uit repo
        Optional<User> possibleUser = userRepo.findByUsername(user.getUsername());

        // 2 bestaat deze gebruiker niet? => return Optional.empty()
        if (possibleUser.isEmpty()) {
            return Optional.empty();
        } else {

            // 3 De gebruiker bestaat: matcht het password?
            User foundUser = possibleUser.get();
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                return possibleUser;
            } else {
                return Optional.empty();
            }
            // 4: Ja, return de gebruiker (eventueel zonder password)
            // 5 Nee: return Optional.empty()

        }

    }


}