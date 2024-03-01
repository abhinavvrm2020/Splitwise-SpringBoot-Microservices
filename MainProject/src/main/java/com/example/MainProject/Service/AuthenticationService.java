package com.example.MainProject.Service;

import com.example.MainProject.Schema.Role;
import com.example.MainProject.Repository.UserRepository;
import com.example.MainProject.Schema.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    public boolean CheckIfUserExist(String responseEmail,String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.getUsernameFromToken(token);
        // check if email exist or not.
        if(email == null) {
            return false;
        } else {
            // check if loggedin user email is same as authheader email
            if(email.equals(responseEmail)) {
                return true;
            } else {
                return false;
            }
        }
    }
    public Integer findLoggedInUserId(String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.getUsernameFromToken(token);
        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow();
        return loggedInUser.getId();
    }

    public boolean ValidationCheck(RegisterRequest request) {
        if(request.getName() == null) {
            return false;
        }

        if(request.getEmail() == null) {
            return false;
        }

        if(request.getPassword() == null) {
            return false;
        }
        return true;
    }

    public User saveUser (RegisterRequest request) {
        boolean check = ValidationCheck(request);
        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(newUser);
        return newUser;
    }

    public String performUpdates(Integer id, User newDetails,String authHeader) {
        String token = authHeader.substring(7);
        User currentUser = userRepository.findById(id)
                .orElseThrow();
        if(token != "" && jwtService.getUsernameFromToken(token).equals(currentUser.getEmail())) {
            if(newDetails.getName() != null) {
                currentUser.setName(newDetails.getName());
            }
            if(newDetails.getEmail() != null) {
                currentUser.setEmail(newDetails.getEmail());
            }
            if(newDetails.getPassword() != null) {
                currentUser.setPassword(passwordEncoder.encode(newDetails.getPassword()));
            }
            userRepository.save(currentUser);
            return "Update Successful";
        } else {
            return "Log In First";
        }
    }

    public AuthenticationResponse register(RegisterRequest request) {
        User newUser = saveUser(request);
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String jwtToken = jwtService.generateToken(newUser);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .username(userDetails.getUsername()).build(); // check whether email or name is returned.
        return response;
    }

    public String authenticate(AuthenticationRequest request) {
        try {
            this.doAuthenticate(request.getEmail(), request.getPassword());
            return "User Logged In";
        } catch (Exception e) {
            return "Invalid Details";
        }
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    public String updateDetails(Integer id, User newDetails,String authHeader) {
        String response = performUpdates(id,newDetails,authHeader);
        return response;
    }
    public String deleteUser(Integer id,AuthenticationRequest request, String authHeader) {
        Integer loggedInUserId = findLoggedInUserId(authHeader);
        if(loggedInUserId == id) {
            return "Profile Delete Successful";
        } else {
            return "Login first";
        }
    }
}
