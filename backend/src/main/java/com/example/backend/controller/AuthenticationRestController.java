package com.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.backend.model.Mail;
import com.example.backend.model.PasswordResetToken;
import com.example.backend.model.User;
import com.example.backend.repository.PasswordResetTokenRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtAuthenticationRequest;
import com.example.backend.security.JwtAuthenticationResponse;
import com.example.backend.security.JwtTokenUtil;
import com.example.backend.security.JwtUser;
import com.example.backend.service.EmailService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AuthenticationRestController {

    @Value("${frontend.port}")
    private String port;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtRelated")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired private EmailService emailService;
    @Autowired private PasswordResetTokenRepository tokenRepository;

    /**
     * creates authentication token
     * @param authenticationRequest service
     * @param sitePreference device service
     * @return the token
     * @throws AuthenticationException Exception
     */
    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, SitePreference sitePreference) throws AuthenticationException {

        User user = userService.findByEmail(authenticationRequest.getUsername());
        if (user != null) {
            if (!user.isEnabled()) {
                return ResponseEntity.badRequest().body("User is not active!");
            }
        }

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, sitePreference);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    /**
     * Refresh the token
     * @param request HttpServletRequest
     * @return the refreshed token
     */
    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestParam("registerRequest") String registerRequest, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        try {
            user = mapper.readValue(registerRequest, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Already registered!");
        }
        userService.registerUser(user);

        //create an activation token. PasswordResetToken can be handy for now.
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        //TODO: change email address
        mail.setFrom("valaki@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Activate registration!");

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("signature", "https://testelek.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + port;
        model.put("resetUrl", url + "/activate-registration?token=" + token.getToken() + "&email=" + user.getEmail());
        mail.setModel(model);
        emailService.sendEmail(mail, "activation");
        return ResponseEntity.ok("Successful registration!");
    }

    @PostMapping("/activate-registration")
    public ResponseEntity<?> activateRegistration(@RequestParam("token") String token, @RequestParam("email") String email, HttpServletRequest request) {
        PasswordResetToken savedToken = tokenRepository.findByToken(token);
        if (savedToken == null) {
            return ResponseEntity.badRequest().body("Invalid token!");
        }
        if (savedToken.getExpiryDate().before(Calendar.getInstance().getTime())) {
            return ResponseEntity.badRequest().body("Token has expired!");
        }
        User tokenUser = savedToken.getUser();
        if (tokenUser.getEmail().equals(email)) {
            tokenUser.setEnabled(true);
            userService.saveUser(tokenUser);
            return ResponseEntity.ok("Successful activation!");
        } else {
            return ResponseEntity.badRequest().body("Invalid user!");
        }
    }
}
