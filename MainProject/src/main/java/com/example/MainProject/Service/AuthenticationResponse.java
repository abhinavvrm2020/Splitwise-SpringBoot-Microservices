package com.example.MainProject.Service;

import lombok.*;

@Data
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwtToken;
    private String username;
}