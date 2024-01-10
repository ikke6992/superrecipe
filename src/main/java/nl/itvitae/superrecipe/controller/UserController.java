package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.config.JsonTokenProvider;
import nl.itvitae.superrecipe.model.User;
import nl.itvitae.superrecipe.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JsonTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public User.TokenDTO login(@RequestBody User request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenProvider.generateToken(userDetails);
        return new User.TokenDTO(request.getUsername(), token);
    }

    @PostMapping("/register")
    public User.TokenDTO register(@RequestBody User user) {
        if (userRepo.findByUsername(user.getUsername()).isEmpty()) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles("ROLE_USER");
            userRepo.save(user);
            String token = tokenProvider.generateToken(user);
            return new User.TokenDTO(user.getUsername(), token);
        }
        return new User.TokenDTO("User already exists", "");
    }
}
