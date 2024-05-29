package com.example.ticketbooking.controllers;

import com.example.ticketbooking.dto.AuthResponseDto;
import com.example.ticketbooking.dto.LoginDto;
import com.example.ticketbooking.dto.RefreshTokenRequestDto;
import com.example.ticketbooking.dto.RegisterDto;
import com.example.ticketbooking.entities.RefreshToken;
import com.example.ticketbooking.enums.AuthStatus;
import com.example.ticketbooking.repositories.RoleRepository;
import com.example.ticketbooking.repositories.AppUserRepository;
import com.example.ticketbooking.services.AuthService;
import com.example.ticketbooking.services.RefreshTokenServiceImpl;
import com.example.ticketbooking.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Slf4j
public class AuthController {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final RefreshTokenServiceImpl refreshTokenService;


    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthResponseDto> loginController(@RequestBody LoginDto loginDto) {
        String jwtToken = authService.login(loginDto);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginDto.getUsername());
        AuthResponseDto authResponseDto = new AuthResponseDto(
                jwtToken,
                refreshToken.getRefreshToken(),
                AuthStatus.LOGIN_SUCCESSFULLY
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponseDto);
    }
    @PostMapping("/api/auth/signup")
    public ResponseEntity<AuthResponseDto> signupController(@RequestBody RegisterDto registerDto){
        try{
            String jwtToken = authService.signup(registerDto);

            AuthStatus authStatus = (jwtToken==null) ? AuthStatus.USER_NOT_CREATED : AuthStatus.USER_CREATED;

            HttpStatus httpStatus = (jwtToken==null) ? HttpStatus.CONFLICT : HttpStatus.OK;

            RefreshToken refreshToken =refreshTokenService.createRefreshToken(registerDto.getUsername());

            AuthResponseDto authResponseDto = new AuthResponseDto(
                    jwtToken,
                    refreshToken.getRefreshToken(),
                    authStatus
            );

            return ResponseEntity
                    .status(httpStatus)
                    .body(authResponseDto);
        }catch (Exception e){
            AuthResponseDto authResponseDto = new AuthResponseDto(
                    null,
                    null,
                    AuthStatus.USER_NOT_CREATED
            );

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(authResponseDto);
        }

    }

    @PostMapping("/api/refreshToken")
    public ResponseEntity<AuthResponseDto> refreshTokenController(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){

            return refreshTokenService.findbyToken(refreshTokenRequestDto.getToken())
                    .map(refreshTokenService::verifyTokenExpiry)
                    .map(RefreshToken::getAppUser)
                    .map(appUserInfo->{
                        String jwtToken  = JwtUtils.generateToken(appUserInfo.getUsername());
                        RefreshToken refreshToken = refreshTokenService.createRefreshToken(appUserInfo.getUsername());

                        AuthResponseDto authResponseDto =  new AuthResponseDto(
                                jwtToken,
                                refreshToken.getRefreshToken(),
                                AuthStatus.AUTHORISED
                        );
                        return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(authResponseDto);


                    }).orElseThrow(() ->new RuntimeException("Refresh Token not found"));

    }


    @GetMapping("/api/home")
    public ResponseEntity<String> homePageController(@RequestBody String s){
        return ResponseEntity.ok("<h1>Hello</h1>");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ping")
    public ResponseEntity<String> pingController(){
        return ResponseEntity.ok("<h1>Hello from backend</h1>");
    }
}
