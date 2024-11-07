package com.dbfinal.prac.controllers;

import com.dbfinal.prac.dto.LoginUserDto;
import com.dbfinal.prac.dto.ProductShowDto;
import com.dbfinal.prac.dto.RegisterUserDto;
import com.dbfinal.prac.dto.UserShowDto;
import com.dbfinal.prac.models.Product;
import com.dbfinal.prac.models.User;
import com.dbfinal.prac.responses.LoginResponse;
import com.dbfinal.prac.services.AuthenticationService;
import com.dbfinal.prac.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime()).setUser(convertToDto(authenticatedUser));

        return ResponseEntity.ok(loginResponse);
    }

    public UserShowDto convertToDto(User user) {
        return new UserShowDto(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getEmail(),
                user.getBalance()
        );
    }
}
