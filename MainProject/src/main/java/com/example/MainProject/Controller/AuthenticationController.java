package com.example.MainProject.Controller;

import com.example.MainProject.Service.AuthenticationRequest;
import com.example.MainProject.Service.AuthenticationResponse;
import com.example.MainProject.Service.AuthenticationService;
import com.example.MainProject.Service.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.MainProject.Schema.User.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register (
        @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("login")
    public ResponseEntity<String> authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<String> updateDetails (
            @PathVariable Integer id,
            @RequestBody User newDetails,
            @RequestHeader("Authorization") String header
    ) {
        return ResponseEntity.ok(authenticationService.updateDetails(id,newDetails,header));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser (
            @PathVariable Integer id,
            @RequestBody AuthenticationRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(authenticationService.deleteUser(id,request,authHeader));
    }
}
