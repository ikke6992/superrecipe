package nl.itvitae.superrecipe.controller;

import nl.itvitae.superrecipe.config.JsonTokenProvider;
import nl.itvitae.superrecipe.model.User;
import nl.itvitae.superrecipe.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepo users;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JsonTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        if (users.findByUsername(user.getUsername()).isEmpty()) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles("ROLE_USER");
            users.save(user);
            String token = tokenProvider.generateToken(user);
            return new User.TokenDTO(user.getUsername(), token);
        }
        return new User.TokenDTO("User already exists", "");
    }
}
